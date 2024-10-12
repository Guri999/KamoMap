package kr.co.map.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kr.co.map.MapRoute
import kr.co.navigation.MainRoute

fun NavGraphBuilder.mapNavGraph(
    popBackStack: () -> Unit,
    onShowErrorSnackBar: (message: String) -> Unit,
) {
    composable<MainRoute.Map> { navBackStackEntry ->
        MapRoute(
            path = with(navBackStackEntry.toRoute<MainRoute.Map>()) { origin to destination },
            popBackStack = popBackStack,
            onShowErrorSnackBar = onShowErrorSnackBar
        )
    }
}
