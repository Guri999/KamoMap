package kr.co.map

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
import javax.inject.Inject

@HiltViewModel
internal class MapViewModel @Inject constructor(
    private val getRoutesUseCase: GetRoutesUseCase,
    private val getDistanceTimeUseCase: GetDistanceTimeUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _error: MutableSharedFlow<KamoException> = MutableSharedFlow(1)
    val error = _error.asSharedFlow()

    private val _unknownError = MutableSharedFlow<String>(1)
    val unknownError = _unknownError.asSharedFlow()

    private val routeCeh = CoroutineExceptionHandler { _, e ->
        viewModelScope.launch {
            if (e is KamoException) {
                _error.emit(e)
            } else {
                _unknownError.emit("경로 조회 API의 에러")
            }
        }
    }

    private val distanceTimeCeh = CoroutineExceptionHandler { _, e ->
        viewModelScope.launch {
            if (e is KamoException) {
                _error.emit(e)
            } else {
                _unknownError.emit("시간 / 거리 조회 API의 에러")
            }
        }
    }

    fun reportMapError(e: Exception?) {
        _unknownError.tryEmit("KakaoMap SDK Error")
    }

    fun initialize(path: Pair<String, String>) {
        _state.update {
            state.value.copy(
                origin = path.first,
                destination = path.second
            )
        }
        initializeRoutes()
    }

    private fun initializeRoutes() {
        viewModelScope.launch(routeCeh) {
            GetRoutesUseCase.Params(
                origin = state.value.origin,
                destination = state.value.destination
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
                origin = state.value.origin,
                destination = state.value.destination
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
        val origin: String = "",
        val destination: String = "",
        val routes: List<Route> = emptyList(),
        val distanceTime: DistanceTime? = null,
    )
}
