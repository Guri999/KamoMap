package kr.co.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.co.common.ext.debugLog
import kr.co.common.model.EntityWrapper
import kr.co.domain.usecase.location.GetLocationsUseCase
import kr.co.domain.usecase.location.GetRoutesUseCase
import kr.co.location.model.LocationsIntent
import kr.co.location.model.LocationsUiState
import kr.co.location.model.LocationsViewState
import javax.inject.Inject

@HiltViewModel
internal class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getRoutesUseCase: GetRoutesUseCase,
) : ViewModel() {

    private val _intentFlow = MutableSharedFlow<LocationsIntent>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val intentFlow = _intentFlow.asSharedFlow()

    private val initial = LocationsViewState.initial()

    val viewState: StateFlow<LocationsViewState> =
        intentFlow
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
        flow { emit(getLocationsUseCase()) }
            .map { result ->
                when (result) {
                    is EntityWrapper.Success -> LocationsUiState.Locations(result.data)
                    is EntityWrapper.Error ->
                        LocationsUiState.Error(
                            code = result.error.code,
                            message = result.error.message,
                            location = result.error.location,
                        )
                }
            }
            .onStart { LocationsUiState.Loading }
            .debugLog("getLocations")


    private fun Flow<LocationsIntent.OnPathClick>.toRoutesCheckedFlow(): Flow<LocationsUiState> =
        map { path ->
            GetRoutesUseCase.Params(
                origin = path.location.first,
                destination = path.location.second
            ).let { getRoutesUseCase(it) }
                .let { result ->
                    when (result) {
                        is EntityWrapper.Success -> LocationsUiState.Navigate(path.location)
                        is EntityWrapper.Error ->
                            LocationsUiState.Error(
                                code = result.error.code,
                                message = result.error.message,
                                origin = path.location.first,
                                destination = path.location.second,
                                location = result.error.location
                            )
                    }
                }
        }.debugLog("Routes Checked")

    private fun processIntent(intent: LocationsIntent) {
        viewModelScope.launch {
            _intentFlow.emit(intent)
        }
    }

    fun onPathClick(path: Pair<String, String>) {
        processIntent(LocationsIntent.OnPathClick(path))
    }

    fun onFallBack() {
        processIntent(LocationsIntent.Initial)
    }

    init {
        processIntent(LocationsIntent.Initial)
    }

}