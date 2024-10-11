package kr.co.map

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.vectormap.MapView
import kr.co.common.util.timeFormat
import kr.co.map.service.setCallBack
import kr.co.ui.theme.KakaoTheme
import kr.co.ui.widget.KamoTopAppBar
import kr.co.ui.widget.UnknownErrorDialog
import java.util.Locale

@Composable
internal fun MapRoute(
    popBackStack: () -> Unit,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    var mapView by remember { mutableStateOf<MapView?>(null) }

    val (showErrorDialog, setShowErrorDialog) = remember { mutableStateOf<String?>(null) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_RESUME -> mapView?.resume()
                Lifecycle.Event.ON_PAUSE -> mapView?.pause()
                Lifecycle.Event.ON_DESTROY -> mapView?.finish()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView?.finish()
        }
    }

    LaunchedEffect(viewModel.unknownError) {
        viewModel.unknownError.collect {
            setShowErrorDialog(it)
        }
    }

    BackHandler {
        popBackStack()
    }

    if (showErrorDialog != null) {
        UnknownErrorDialog(
            errorLocation = showErrorDialog
        ) { setShowErrorDialog(null) }
    }

    MapScreen(
        state = state,
        setMapView = { mapView = it },
        reportMapError = viewModel::reportMapError,
        popBackStack = popBackStack
    )
}

@Composable
private fun MapScreen(
    state: MapViewModel.State = MapViewModel.State(),
    setMapView: (MapView) -> Unit = {},
    reportMapError: (Exception?) -> Unit = {},
    popBackStack: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            KamoTopAppBar(
                title = "경로 탐색",
                onNavClick = popBackStack,
            )
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            contentAlignment = Alignment.Center
        ) {
            if (state.routes.isEmpty()) {
                CircularProgressIndicator()
            } else {
                AndroidView(
                    factory = { context ->
                        MapView(context)
                            .setCallBack(
                                reportMapError = reportMapError,
                                routes = state.routes
                            )
                            .also(setMapView)
                    }
                )

                Column(
                    modifier = Modifier
                        .padding(40.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(8.dp),
                            ambientColor = Color.Black.copy(0.12f),
                            spotColor = Color.Black.copy(0.12f)
                            )
                        .background(
                            color = Color.Black.copy(0.8f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(24.dp)
                        .align(Alignment.BottomEnd),
                ) {
                    Text(
                        text = "시간 : ${state.distanceTime?.time?.let { timeFormat(it) }}분\n"
                        + "거리 : ${String.format(Locale.getDefault(),"%,d",state.distanceTime?.distance)}m",
                        style = KakaoTheme.typography.body1R,
                        color = KakaoTheme.colors.okay,
                    )
                }
            }
        }
    }
}
