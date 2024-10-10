package kr.co.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kr.co.location.navigation.LOCATION_ROUTE
import kr.co.location.navigation.locationsNavGraph
import kr.co.map.navigation.MAP_ROUTE
import kr.co.map.navigation.mapNavGraph

@Composable
internal fun MainNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = LOCATION_ROUTE,
    ) {
        locationsNavGraph(
            navigateToMap = { (origin, destination) ->
                navController.navigate(
                    "$MAP_ROUTE/$origin/$destination"
                )
            }
        )

        mapNavGraph(
            popBackStack = {
                navController.navigate(LOCATION_ROUTE) {
                    popUpTo(LOCATION_ROUTE) {
                        inclusive = true
                    }
                }
            }
        )
    }
}