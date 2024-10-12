package kr.co.model

data class Locations(
    val locations: List<Location>,
) {
    data class Location(
        val origin: String,
        val destination: String,
    )
}
