package kr.co.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kr.co.main.navigation.MainNavHost
import kr.co.ui.widget.KamoSnackBarHost

@Composable
internal fun MainRoute(
    navController: NavHostController = rememberNavController(),
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()
    val onShowErrorSnackBar: (message: String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = message
            )
        }
    }

    MainScreen(
        navController = navController,
        snackBarHostState = snackBarHostState,
        onShowErrorSnackBar = onShowErrorSnackBar
    )
}

@Composable
internal fun MainScreen(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    onShowErrorSnackBar: (message: String) -> Unit,
) {
    Scaffold(
        snackbarHost = { KamoSnackBarHost(snackBarHostState) }
    ) { 
        MainNavHost(
            navController = navController,
            onShowErrorSnackBar = onShowErrorSnackBar
        )
    }
}