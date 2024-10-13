package kr.co.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.co.common.model.EntityWrapper
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

    private val _error: MutableSharedFlow<String> = MutableSharedFlow(1)
    val error = _error.asSharedFlow()

    fun reportMapError(e: Exception?) {
        viewModelScope.launch {
            _error.emit("KakaoMap SDK Error")
        }
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
        viewModelScope.launch {
            val deferred = async {
                GetRoutesUseCase.Params(
                    origin = state.value.origin,
                    destination = state.value.destination
                ).run {
                    getRoutesUseCase(this)
                }.also { result ->
                    when (result) {
                        is EntityWrapper.Success -> _state.update { state.value.copy(routes = result.data) }
                        is EntityWrapper.Error -> _error.emit(result.error.location)
                    }
                }
            }

            async {
                GetDistanceTimeUseCase.Params(
                    origin = state.value.origin,
                    destination = state.value.destination
                ).run {
                    getDistanceTimeUseCase(this)
                }.also { result ->
                    when (result) {
                        is EntityWrapper.Success -> _state.update { state.value.copy(distanceTime = result.data) }
                        is EntityWrapper.Error -> _error.emit(result.error.location)
                    }
                }
            }.await()
            deferred.await()
        }

    }

    data class State(
        val origin: String = "",
        val destination: String = "",
        val routes: List<kr.co.model.Route> = emptyList(),
        val distanceTime: kr.co.model.DistanceTime? = null,
    )
}
