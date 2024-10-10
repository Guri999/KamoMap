package kr.co.map

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.domain.model.DistanceTime
import kr.co.domain.model.Route
import kr.co.domain.usecase.GetDistanceTimeUseCase
import kr.co.domain.usecase.GetRoutesUseCase
import kr.co.map.navigation.DESTINATION_KEY
import kr.co.map.navigation.ORIGIN_KEY
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MapViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRoutesUseCase: GetRoutesUseCase,
    private val getDistanceTimeUseCase: GetDistanceTimeUseCase,
) : BaseViewModel<MapViewModel.State>() {

    private val origin: String = checkNotNull(savedStateHandle.get<String>(ORIGIN_KEY))

    private val destination: String = checkNotNull(savedStateHandle.get<String>(DESTINATION_KEY))

    init {
        launch {
            GetRoutesUseCase.Params(
                origin = origin,
                destination = destination
            ).run {
                getRoutesUseCase(this)
            }.also {
                updateState {
                    copy(routes = it)
                }
            }
        }

        launch {
            GetDistanceTimeUseCase.Params(
                origin = origin,
                destination = destination
            ).run {
                getDistanceTimeUseCase(this)
            }.also {
                updateState {
                    copy(distanceTime = it)
                }
            }
        }
    }

    override fun createInitialState(): State = State()

    data class State(
        val routes: List<Route> = emptyList(),
        val distanceTime: DistanceTime? = null,
    ) : BaseViewModel.State
}
