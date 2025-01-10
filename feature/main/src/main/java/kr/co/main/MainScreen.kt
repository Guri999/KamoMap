package kr.co.main

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kr.co.kamo.feature.main.R
import kr.co.main.navigation.MainNavHost
import kr.co.ui.widget.KamoSnackBarHost

@Composable
internal fun MainRoute(
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()
    val onShowErrorSnackBar: (message: String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = context.getString(
                    R.string.main_route_snack_error, when (message) {
                        LOCATION_API -> context.getString(R.string.main_route_locations_api)
                        ROUTES_API -> context.getString(R.string.main_route_routes_api)
                        DISTANCE_TIME_API -> context.getString(R.string.main_route_distance_time_api)
                        else -> message
                    }
                )
            )
        }
    }


    MainScreen(
        navController = navController,
        snackBarHostState = snackBarHostState,
        onShowErrorSnackBar = onShowErrorSnackBar
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

private const val LOCATION_API = "location"
private const val ROUTES_API = "routes"
private const val DISTANCE_TIME_API = "distance-time"