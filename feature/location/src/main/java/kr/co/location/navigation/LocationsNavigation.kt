package kr.co.location.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.location.LocationsRoute

const val LOCATION_ROUTE = "locationRoute"

fun NavGraphBuilder.locationsNavGraph(
    navigateToMap: (Pair<String, String>) -> Unit
) {
    composable(LOCATION_ROUTE) {
        LocationsRoute(
            navigateToMap = navigateToMap
        )
    }
}