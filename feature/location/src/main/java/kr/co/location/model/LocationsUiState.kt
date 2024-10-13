package kr.co.location.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kr.co.location.model.LocationsUiState.Locations.Location

typealias LocationsEntity = kr.co.model.Locations

@Stable
internal sealed interface LocationsUiState {
    fun reduce(viewState: LocationsViewState) =
        when (this) {
            is Locations ->
                viewState.copy(
                    model = locations,
                    uiState = this
                )

            else -> viewState.copy(
                uiState = this
            )
        }

    @Immutable
    data object Loading : LocationsUiState

    @Immutable
    data class Locations(
        val locations: List<Location>,
    ) : LocationsUiState {

        constructor(locations: LocationsEntity) : this(
            locations = locations.locations.map {
                Location(
                    origin = it.origin,
                    destination = it.destination,
                )
            }
        )

        @Immutable
        data class Location(
            val origin: String,
            val destination: String,
        )
    }

    @Immutable
    data class Navigate(
        val location: Pair<String, String>,
    ) : LocationsUiState

    @Immutable
    data class Error(
        val code: Int?,
        val message: String?,
        val localizedMessage: String?,
        val origin: String? = null,
        val destination: String? = null,
        val errorUrl: String,
    ) : LocationsUiState

}

@Stable
internal data class LocationsViewState(
    val model: List<Location>,
    val uiState: LocationsUiState,
) {
    companion object {
        fun initial() = LocationsViewState(
            model = emptyList(),
            uiState = LocationsUiState.Loading,
        )
    }

    override fun toString(): String {
        return "locations: $model\nuiState: ${uiState::class.simpleName}"
    }
}