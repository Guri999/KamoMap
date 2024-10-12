package kr.co.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kr.co.common.ext.debugLog
import kr.co.common.ext.startWith
import kr.co.common.model.KamoException
import kr.co.domain.usecase.GetLocationsUseCase
import kr.co.domain.usecase.GetRoutesUseCase
import kr.co.location.model.LocationsIntent
import kr.co.location.model.LocationsUiState
import kr.co.location.model.LocationsViewState
import javax.inject.Inject

@HiltViewModel
internal class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getRoutesUseCase: GetRoutesUseCase,
) : ViewModel() {

    private val _intentFlow = MutableSharedFlow<LocationsIntent>(extraBufferCapacity = 64)
    private val intentFlow = _intentFlow.asSharedFlow()

    private val initial = LocationsViewState.initial()

    val viewState: StateFlow<LocationsViewState> =
        intentFlow
            .filtered()
            .debugLog("intentFlow")
            .toLocationUiChangedFlow()
            .debugLog("uiStateFlow")
            .scan(initial) { vs, change -> change.reduce(vs) }
            .debugLog("viewState")
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = initial
            )

    private fun SharedFlow<LocationsIntent>.filtered(): Flow<LocationsIntent> =
        merge(
            filterIsInstance<LocationsIntent.Initial>(),
            filterNot { it is LocationsIntent.Initial }
        )

    private fun Flow<LocationsIntent>.toLocationUiChangedFlow(): Flow<LocationsUiState> =
        merge(
            filterIsInstance<LocationsIntent.Initial>()
                .toInitializeOrFallback(),
            filterIsInstance<LocationsIntent.OnPathClick>()
                .toRoutesCheckedFlow(),
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<LocationsIntent.Initial>.toInitializeOrFallback(): Flow<LocationsUiState> =
        flatMapLatest {
            if (viewState.value.model.isEmpty()) {
                getLocationsFlow()
            } else {
                flowOf(LocationsUiState.Locations(viewState.value.model))
            }
        }

    private fun getLocationsFlow(): Flow<LocationsUiState> =
        try {
            flow { emit(getLocationsUseCase()) }
                .map(LocationsUiState::Locations)
                .startWith(LocationsUiState.Loading)
                .debugLog("getLocations")
        } catch (e: Exception) {
            if (e is KamoException) {
                LocationsUiState.Error.KamoError(
                    code = e.code ?: 0,
                    message = e.message ?: "Unknown Error",
                    localizedMessage = e.localizedMessage ?: "알 수없는 에러",
                )
            } else {
                LocationsUiState.Error.UnknownError(
                    apiName = "출발지 / 도착지 리스트 API",
                )
            }.let {
                flow { emit(it) }
            }.debugLog("Locations Error")
        }


    private fun Flow<LocationsIntent.OnPathClick>.toRoutesCheckedFlow(): Flow<LocationsUiState> =
        map { path ->
            try {
                GetRoutesUseCase.Params(
                    origin = path.location.first,
                    destination = path.location.second
                ).let { getRoutesUseCase(it) }

                LocationsUiState.Navigate(path.location)
            } catch (e: Exception) {
                if (e is KamoException) {
                    LocationsUiState.Error.KamoError(
                        code = e.code,
                        message = e.message ?: "Unknown Error",
                        localizedMessage = e.localizedMessage ?: "알 수없는 에러",
                        origin = path.location.first,
                        destination = path.location.second,
                    )
                } else {
                    LocationsUiState.Error.UnknownError(
                        apiName = "경로 조회 API",
                    )
                }
            }
        }.debugLog("Routes Checked")

    fun processIntent(intent: LocationsIntent) {
        _intentFlow.tryEmit(intent)
    }
}