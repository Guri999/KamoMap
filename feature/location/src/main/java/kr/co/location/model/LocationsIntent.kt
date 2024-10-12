package kr.co.location.model

internal interface LocationsIntent {

    data object Initial : LocationsIntent

    data class OnPathClick(val location: Pair<String, String>) : LocationsIntent
}