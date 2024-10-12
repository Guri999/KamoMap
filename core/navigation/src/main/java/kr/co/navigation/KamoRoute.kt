package kr.co.navigation

import kotlinx.serialization.Serializable

sealed interface KamoRoute {

    @Serializable
    data object Main : KamoRoute

    @Serializable
    data object Splash : KamoRoute
}

sealed interface MainRoute : KamoRoute {

    @Serializable
    data object Location : MainRoute

    @Serializable
    data class Map(val origin: String, val destination: String) : MainRoute
}
