package kr.co.map.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.co.map.MapRoute
import kr.co.navigation.MainRoute

internal const val ORIGIN_KEY = "origin"
internal const val DESTINATION_KEY = "destination"

fun NavGraphBuilder.mapNavGraph(
    popBackStack: () -> Unit,
    onShowErrorSnackBar: (message: String) -> Unit
) {
    composable(
        route = MainRoute.Map.route,
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
            popBackStack = popBackStack,
            onShowErrorSnackBar = onShowErrorSnackBar
        )
    }
}
