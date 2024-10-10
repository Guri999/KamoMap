package kr.co.kamo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kr.co.main.navigation.mainNavGraph
import kr.co.navigation.KamoRoute
import kr.co.ui.theme.KaKaoTheme

@Composable
internal fun KakaoNavHost(
    navController: NavHostController = rememberNavController(),
) {
    var navRoute by remember { mutableStateOf(KamoRoute.Main) }

    KakaoNavScreen(
        startDestinationRoute = navRoute,
        navController = navController
    )
}

@Composable
private fun KakaoNavScreen(
    navController: NavHostController,
    startDestinationRoute: KamoRoute = KamoRoute.Main,
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute.route,
    ) {
        mainNavGraph()
    }
}

@Preview
@Composable
private fun Preview() {
    KaKaoTheme {

    }
}