package kr.co.map

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.vectormap.MapView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.transform
import kr.co.common.util.timeFormat
import kr.co.map.service.setCallBack
import kr.co.ui.theme.KamoTheme
import java.util.Locale

@Composable
internal fun MapRoute(
    viewModel: MapViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
    onShowErrorSnackBar: (message: String) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    var mapView by remember { mutableStateOf<MapView?>(null) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
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

    LaunchedEffect(Unit) {
        with(viewModel) {
            merge(
                error.transform { emit(it.message.orEmpty()) },
                unknownError,
            ).collectLatest {
                onShowErrorSnackBar(it)
                popBackStack()
            }
        }
    }

    BackHandler {
        popBackStack()
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
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

            state.distanceTime?.let {
                MapWidgetScreen(
                    origin = state.origin,
                    destination = state.destination,
                    time = it.time,
                    distance = it.distance,
                    popBackStack = popBackStack
                )
            }
        }
    }
}

@Composable
private fun BoxScope.MapWidgetScreen(
    origin: String,
    destination: String,
    time: Int,
    distance: Int,
    popBackStack: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter)
            .systemBarsPadding()
            .padding(
                vertical = 36.dp,
                horizontal = 20.dp
            )
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                spotColor = Color.Black.copy(0.25f),
                ambientColor = Color.Black.copy(0.25f)
            )
            .background(KamoTheme.colors.white)
            .clip(CircleShape)
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = popBackStack
        ) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                imageVector = Icons.Filled.Close,
                contentDescription = "close",
                tint = KamoTheme.colors.grey2
            )
        }

        Text(
            text = origin,
            style = KamoTheme.typography.body1R,
            color = KamoTheme.colors.grey2,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Icon(
            modifier = Modifier.size(12.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = "~",
            tint = KamoTheme.colors.grey4
        )

        Text(
            text = destination,
            style = KamoTheme.typography.body1R,
            color = KamoTheme.colors.grey3,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
    }
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
            text = "시간 : ${timeFormat(time)}분\n"
                    + "거리 : ${
                String.format(
                    Locale.getDefault(),
                    "%,d",
                    distance
                )
            }m",
            style = KamoTheme.typography.body1R,
            color = KamoTheme.colors.white,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    KamoTheme {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            MapWidgetScreen("서울역", "홍대입구역", 3000, 2000) {}
        }
    }

}