package kr.co.location

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.location.model.LocationsIntent
import kr.co.location.model.LocationsUiState
import kr.co.ui.theme.KamoTheme
import kr.co.ui.widget.KamoErrorBottomSheet
import kr.co.ui.widget.KamoTopAppBar
import kr.co.ui.widget.UnknownErrorDialog

private typealias LocationPath = Pair<String, String>
private typealias Error = LocationsUiState.Error.KamoError
private typealias UnknownError = LocationsUiState.Error.UnknownError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LocationsRoute(
    viewModel: LocationsViewModel = hiltViewModel(),
    navigateToMap: (LocationPath) -> Unit = {},
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        viewModel.processIntent(LocationsIntent.Initial)

        onDispose {  }
    }

    LocationsScreen(
        model = state.model,
        onPathClick = { viewModel.processIntent(LocationsIntent.OnPathClick(it)) }
    )

    when (state.uiState) {
        is Error -> {
            val error = state.uiState as Error
            KamoErrorBottomSheet(
                title = error.localizedMessage,
                start = error.origin,
                end = error.destination,
                code = error.code,
                message = error.message,
                onDismissRequest = { viewModel.processIntent(LocationsIntent.Initial)}
            )
        }

        is UnknownError -> {
            UnknownErrorDialog(
                errorLocation = (state as UnknownError).apiName
            ) { viewModel.processIntent(LocationsIntent.Initial) }
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

        is LocationsUiState.Navigate -> {
            navigateToMap((state.uiState as LocationsUiState.Navigate).location)
        }

        else -> Unit
    }
}

@Composable
private fun LocationsScreen(
    model: List<LocationsUiState.Locations.Location> = emptyList(),
    onPathClick: (LocationPath) -> Unit = {},
) {
    Scaffold(
        topBar = {
            KamoTopAppBar(
                title = "카카오 모빌리티 2차 과제"
            )
        },
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
                .background(KamoTheme.colors.bg)
        ) {
            items(model) {
                PathBox(
                    start = it.origin,
                    end = it.destination,
                    onPathClick = onPathClick
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = KamoTheme.colors.stroke
                )
            }
        }
    }
}

@Composable
private fun PathBox(
    start: String,
    end: String,
    onPathClick: (LocationPath) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onPathClick(start to end) })
            .padding(16.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                append("출발지 : ")

                withStyle(
                    style = KamoTheme.typography.body1Sb.copy(color = KamoTheme.colors.emphatic)
                        .toSpanStyle()
                ) {
                    append("$start\n")
                }

                append("도착지 : ")

                withStyle(
                    style = KamoTheme.typography.body1Sb.copy(color = KamoTheme.colors.emphatic2)
                        .toSpanStyle()
                ) {
                    append(end)
                }
            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    KamoTheme {
        LocationsScreen()
    }
}