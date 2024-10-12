package kr.co.location.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.location.LocationsRoute
import kr.co.navigation.MainRoute

fun NavGraphBuilder.locationsNavGraph(
    navigateToMap: (Pair<String, String>) -> Unit,
    onShowErrorSnackBar: (message: String) -> Unit = {}
) {
    composable(MainRoute.Location.route) {
        LocationsRoute(
            navigateToMap = navigateToMap,
            onShowErrorSnackBar = onShowErrorSnackBar
        )
    }
}