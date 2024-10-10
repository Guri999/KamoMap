package kr.co.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kr.co.location.navigation.locationsNavGraph
import kr.co.map.navigation.mapNavGraph
import kr.co.navigation.MainRoute

@Composable
internal fun MainNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute.Location.route,
    ) {
        locationsNavGraph(
            navigateToMap = { (origin, destination) ->
                navController.navigate(
                    MainRoute.Map.createRoute(origin, destination)
                )
            }
        )

        mapNavGraph(
            popBackStack = {
                navController.navigate(MainRoute.Location.route) {
                    popUpTo(MainRoute.Location.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}