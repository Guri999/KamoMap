package kr.co.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kr.co.location.model.LocationsIntent
import kr.co.location.model.LocationsUiState
import kr.co.location.model.LocationsViewState
import kr.co.ui.icon.KamoIcon
import kr.co.ui.icon.kamoicon.Car
import kr.co.ui.theme.KamoTheme
import kr.co.ui.widget.KamoErrorBottomSheet

private typealias LocationPath = Pair<String, String>

@Composable
internal fun LocationsRoute(
    viewModel: LocationsViewModel = hiltViewModel(),
    navigateToMap: (LocationPath) -> Unit = {},
    onShowErrorSnackBar: (message: String) -> Unit = {},
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.processIntent(LocationsIntent.Initial)
    }

    LaunchedEffect(Unit) {
        viewModel.viewState.collectLatest {
            if (
                it.uiState is LocationsUiState.Locations
                && it.model.isEmpty()
            ) {
                delay(1_000)
                viewModel.processIntent(LocationsIntent.Initial)
            }
        }
    }

    LocationsContent(
        state = state,
        onPathClick = { viewModel.processIntent(LocationsIntent.OnPathClick(it)) },
        onFallBack = { viewModel.processIntent(LocationsIntent.Initial) },
        onShowErrorSnackBar = onShowErrorSnackBar,
        navigateToMap = navigateToMap
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationsContent(
    state: LocationsViewState,
    onPathClick: (LocationPath) -> Unit = {},
    onFallBack: () -> Unit = {},
    onShowErrorSnackBar: (message: String) -> Unit = {},
    navigateToMap: (LocationPath) -> Unit = {},
) {
    LocationsScreen(
        model = state.model,
        onPathClick = onPathClick
    )

    when (state.uiState) {
        is LocationsUiState.Error -> {
            val error = state.uiState
            if (error.code != null && error.message != null && error.localizedMessage != null) {
                KamoErrorBottomSheet(
                    title = error.localizedMessage,
                    start = error.origin,
                    end = error.destination,
                    code = error.code,
                    message = error.message,
                    onDismissRequest = onFallBack
                )
            } else {
                onShowErrorSnackBar(error.errorUrl)
            }
        }

        is LocationsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = KamoTheme.colors.keyColor
                )
            }
        }

        is LocationsUiState.Navigate -> navigateToMap(state.uiState.location)

        else -> Unit
    }
}

@Composable
private fun LocationsScreen(
    model: List<LocationsUiState.Locations.Location> = emptyList(),
    onPathClick: (LocationPath) -> Unit = {},
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(KamoTheme.colors.keyColor3)
            .padding(horizontal = 20.dp)
            .navigationBarsPadding()
    ) {
        item {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "경로 찾기",
                style = KamoTheme.typography.title1Sb,
                color = KamoTheme.colors.black
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        items(model) {
            PathBox(
                start = it.origin,
                end = it.destination,
                onPathClick = onPathClick
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
    }

}

@Composable
private fun PathBox(
    start: String,
    end: String,
    onPathClick: (LocationPath) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = Color.Black.copy(0.25f),
                ambientColor = Color.Black.copy(0.25f)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onPathClick(start to end) }
            .background(KamoTheme.colors.white)
            .padding(24.dp)
            .semantics {
                contentDescription = "$start -> $end 카카오 맵으로 경로 찾기"
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(56.dp)
                .clearAndSetSemantics { },
            imageVector = KamoIcon.Car,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(40.dp))

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = start,
                style = KamoTheme.typography.body1Sb,
                color = KamoTheme.colors.black
            )
            Icon(
                modifier = Modifier
                    .weight(1f)
                    .size(24.dp)
                    .clearAndSetSemantics { },
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = KamoTheme.colors.grey3
            )
            Text(
                modifier = Modifier.weight(1f),
                text = end,
                style = KamoTheme.typography.body1Sb,
                color = KamoTheme.colors.keyColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    KamoTheme {
        LocationsScreen(
            model = listOf(
                LocationsUiState.Locations.Location(
                    origin = "에버랜드",
                    destination = "서울랜드"
                ),
                LocationsUiState.Locations.Location(
                    origin = "서울역",
                    destination = "서울랜드"
                ),
            )
        )
    }
}