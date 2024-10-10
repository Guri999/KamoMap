package kr.co.location

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.common.model.KamoException
import kr.co.ui.theme.KaKaoTheme
import kr.co.ui.theme.KakaoTheme
import kr.co.ui.widget.KamoErrorBottomSheet
import kr.co.ui.widget.KamoTopAppBar

typealias LocationPath = Pair<String, String>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LocationsRoute(
    viewModel: LocationsViewModel = hiltViewModel(),
    navigateToMap: (LocationPath) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var error by remember { mutableStateOf<KamoException?>(null) }
    var errorLocation by remember { mutableStateOf<String?>(null) }

    val (showErrorSheet, setShowErrorSheet) = remember { mutableStateOf(false) }
    val (showErrorDialog, setShowErrorDialog) = remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.navigateToMap) {
        viewModel.navigateToMap.collect(navigateToMap)
    }

    LaunchedEffect(viewModel.error) {
        viewModel.error.collect {
            error = it
            setShowErrorSheet(true)
        }
    }

    LaunchedEffect(viewModel.unknownError) {
        viewModel.unknownError.collect {
            errorLocation = it
            setShowErrorDialog(true)
        }
    }

    if (showErrorSheet) {
        KamoErrorBottomSheet(
            title = error?.localizedMessage?: "알 수 없는 에러",
            start = error?.extras?.get("origin").toString(),
            end = error?.extras?.get("destination").toString(),
            code = error?.code,
            message = error?.message ?: "Unknown Error",
            onDismissRequest = { setShowErrorSheet(false) }
        )
    }
    
    if (showErrorDialog) {
        Dialog(
            onDismissRequest = { setShowErrorDialog(false) }
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(KakaoTheme.colors.bg)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "에러가 발생했습니다.",
                    style = KakaoTheme.typography.title2Sb,
                    color = KakaoTheme.colors.text,
                )

                Text(
                    text = "발생 위치: $errorLocation",
                    style = KakaoTheme.typography.body2R,
                    color = KakaoTheme.colors.stroke
                )

                TextButton (
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { setShowErrorDialog(false) },
                ) {
                    Text(
                        text = "확인",
                        style = KakaoTheme.typography.body1Sb,
                        color = KakaoTheme.colors.emphatic2
                    )
                }
            }
        }
    }

    LocationsScreen(
        state = state,
        onPathClick = viewModel::onPathClick
    )
}

@Composable
private fun LocationsScreen(
    state: LocationsViewModel.State = LocationsViewModel.State(),
    onPathClick: (LocationPath) -> Unit = {}
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
                .background(KakaoTheme.colors.bg)
        ) {
            items(state.locations) {
                PathBox(
                    start = it.origin,
                    end = it.destination,
                    onPathClick = onPathClick
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = KakaoTheme.colors.stroke
                )
            }
        }
    }
}

@Composable
private fun PathBox(
    start: String,
    end: String,
    onPathClick: (LocationPath) -> Unit = {}
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
                    style = KakaoTheme.typography.body1Sb.copy(color = KakaoTheme.colors.emphatic)
                        .toSpanStyle()
                ) {
                    append("$start\n")
                }

                append("도착지 : ")

                withStyle(
                    style = KakaoTheme.typography.body1Sb.copy(color = KakaoTheme.colors.emphatic2)
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
    KaKaoTheme {
        LocationsScreen()
    }
}