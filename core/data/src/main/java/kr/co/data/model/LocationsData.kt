package kr.co.data.model

data class LocationsData(
    val locations: List<Location>,
) {
    data class Location(
        val origin: String,
        val destination: String,
    )
}