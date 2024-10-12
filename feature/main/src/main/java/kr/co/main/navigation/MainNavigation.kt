package kr.co.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.main.MainRoute
import kr.co.navigation.KamoRoute

fun NavGraphBuilder.mainNavGraph() {
    composable(KamoRoute.Main.route) {
        MainRoute()
    }
}