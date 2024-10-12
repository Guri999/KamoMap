package kr.co.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetLocationsResponse(

    @SerialName("locations")
    val locations: List<Location>,
) {
    @Serializable
    data class Location(
        @SerialName("origin")
        val origin: String,

        @SerialName("destination")
        val destination: String,
    )
}
