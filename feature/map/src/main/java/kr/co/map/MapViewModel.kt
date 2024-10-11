package kr.co.map

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.co.common.model.KamoException
import kr.co.domain.model.DistanceTime
import kr.co.domain.model.Route
import kr.co.domain.usecase.GetDistanceTimeUseCase
import kr.co.domain.usecase.GetRoutesUseCase
import kr.co.map.navigation.DESTINATION_KEY
import kr.co.map.navigation.ORIGIN_KEY
import javax.inject.Inject

@HiltViewModel
internal class MapViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRoutesUseCase: GetRoutesUseCase,
    private val getDistanceTimeUseCase: GetDistanceTimeUseCase,
) : ViewModel() {
    private val origin: String = checkNotNull(savedStateHandle.get<String>(ORIGIN_KEY))
    private val destination: String = checkNotNull(savedStateHandle.get<String>(DESTINATION_KEY))

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _error: MutableSharedFlow<KamoException> = MutableSharedFlow(1)
    val error = _error.asSharedFlow()

    private val _unknownError = MutableSharedFlow<String>(1)
    val unknownError = _unknownError.asSharedFlow()

    private val routeCeh = CoroutineExceptionHandler { _, e ->
        if (e is KamoException) {
            _error.tryEmit(e)
        } else {
            _unknownError.tryEmit("경로 조회 API의 에러")
        }
    }

    private val distanceTimeCeh = CoroutineExceptionHandler { _, e ->
        if (e is KamoException) {
            _error.tryEmit(e)
        } else {
            _unknownError.tryEmit("시간 / 거리 조회 API의 에러")
        }
    }

    fun reportMapError(e: Exception?) {
        _unknownError.tryEmit("KakaoMap SDK Error")
    }

    init {
        viewModelScope.launch(routeCeh) {
            GetRoutesUseCase.Params(
                origin = origin,
                destination = destination
            ).run {
                getRoutesUseCase(this)
            }.also { data ->
                _state.update {
                    state.value.copy(routes = data)
                }
            }
        }

        viewModelScope.launch(distanceTimeCeh) {
            GetDistanceTimeUseCase.Params(
                origin = origin,
                destination = destination
            ).run {
                getDistanceTimeUseCase(this)
            }.also { data ->
                _state.update {
                    state.value.copy(distanceTime = data)
                }
            }
        }
    }

    data class State(
        val routes: List<Route> = emptyList(),
        val distanceTime: DistanceTime? = null,
    )
}
