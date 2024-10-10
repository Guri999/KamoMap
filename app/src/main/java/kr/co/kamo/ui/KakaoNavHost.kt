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
import kr.co.main.navigation.MAIN_ROUTE
import kr.co.main.navigation.mainNavGraph
import kr.co.ui.theme.KaKaoTheme

private enum class KakaoNavRoute(
    val route: String
) {
    MAIN(MAIN_ROUTE),
}

@Composable
internal fun KakaoNavHost(
    navController: NavHostController = rememberNavController(),
) {
    var navRoute by remember { mutableStateOf(KakaoNavRoute.MAIN) }

    KakaoNavScreen(
        startDestinationRoute = navRoute,
        navController = navController
    )
}

@Composable
private fun KakaoNavScreen(
    navController: NavHostController,
    startDestinationRoute: KakaoNavRoute = KakaoNavRoute.MAIN,
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