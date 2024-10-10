package kr.co.map.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.co.map.MapRoute

const val MAP_ROUTE = "mapRoute"
internal const val ORIGIN_KEY = "origin"
internal const val DESTINATION_KEY = "destination"

fun NavGraphBuilder.mapNavGraph(
    popBackStack: () -> Unit,
) {
    composable(
        route = "$MAP_ROUTE/{origin}/{destination}",
        arguments = listOf(
            navArgument(ORIGIN_KEY) {
                type = NavType.StringType
                nullable = false
            },
            navArgument(DESTINATION_KEY) {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        MapRoute(
            popBackStack = popBackStack
        )
    }
}
