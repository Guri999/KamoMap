package kr.co.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val MAIN_ROUTE = "mainRoute"

fun NavGraphBuilder.mainNavGraph() {
    composable(MAIN_ROUTE) {
        MainNavHost()
    }
}