package kr.co.kamo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kr.co.main.navigation.mainNavGraph
import kr.co.navigation.KamoRoute
import kr.co.ui.theme.KamoTheme

@Composable
internal fun KamoNavHost(
    navController: NavHostController = rememberNavController(),
) {
    var navRoute by remember { mutableStateOf<KamoRoute>(KamoRoute.Splash) }

    LaunchedEffect(Unit) {
        delay(1_500)
        navRoute = KamoRoute.Main
    }

    KamoNavScreen(
        startDestinationRoute = navRoute,
        navController = navController
    )
}

@Composable
private fun KamoNavScreen(
    navController: NavHostController,
    startDestinationRoute: KamoRoute = KamoRoute.Splash,
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
    ) {
        composable<KamoRoute.Splash> {
            SplashRoute()
        }

        mainNavGraph()
    }
}