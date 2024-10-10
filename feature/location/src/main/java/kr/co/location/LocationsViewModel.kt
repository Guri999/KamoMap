package kr.co.location

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kr.co.common.model.KamoException
import kr.co.domain.model.Locations.Location
import kr.co.domain.usecase.GetLocationsUseCase
import kr.co.domain.usecase.GetRoutesUseCase
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getRoutesUseCase: GetRoutesUseCase,
) : BaseViewModel<LocationsViewModel.State>() {

    private val _navigateToMap = MutableSharedFlow<Pair<String, String>>(replay = 1)
    val navigateToMap = _navigateToMap.asSharedFlow()

    fun onPathClick(location: Pair<String, String>) = launch {
        GetRoutesUseCase.Params(
            origin = location.first,
            destination = location.second
        ).run {
            getRoutesUseCase(this)
        }.debugLog("getRoutes")
    }.invokeOnCompletion { e ->
        if (e == null) {
            _navigateToMap.tryEmit(location).debugLog("navigateToMap")
        } else {
            if (e is KamoException) {
                e.apply {
                    addExtras("origin", location.first)
                    addExtras("destination", location.second)
                }.debugLog("KamoException")
                    .also { setError(it) }
            } else {
                setUnknownError("경로 조회 API의 에러")
            }
        }
    }

    init {
        launchWithLoading {
            getLocationsUseCase.invoke()
                .debugLog("getLocations")
                .also { updateState { copy(locations = it.locations) } }
        }.invokeOnCompletion { e ->
            if (e is KamoException) {
                setError(e)
            } else if (e != null) {
                setUnknownError("출발지 / 도착지 리스트 API의 에러")
            }
        }
    }

    data class State(
        val locations: List<Location> = emptyList(),
    ) : BaseViewModel.State

    override fun createInitialState(): State = State()

}
