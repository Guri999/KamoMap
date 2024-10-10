package kr.co.navigation

sealed class KamoRoute(val route: String) {

    data object Main : KamoRoute(MAIN_ROUTE)
}

sealed class MainRoute(route: String) : KamoRoute(route) {

    data object Location : MainRoute(LOCATION_ROUTE)

    data object Map : MainRoute("$MAP_ROUTE/{origin}/{destination}") {
        fun createRoute(origin: String, destination: String) =
            "$LOCATION_ROUTE/$origin/$destination"
    }
}

private const val MAIN_ROUTE = "mainRoute"

private const val LOCATION_ROUTE = "locationRoute"
private const val MAP_ROUTE = "mapRoute"
