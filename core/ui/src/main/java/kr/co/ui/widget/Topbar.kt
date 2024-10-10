package kr.co.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kr.co.ui.theme.KaKaoTheme
import kr.co.ui.theme.KakaoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KamoTopAppBar(
    title: String,
    onNavClick: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = KakaoTheme.typography.body1Sb,
                color = KakaoTheme.colors.text
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = KakaoTheme.colors.stroke,
            titleContentColor = KakaoTheme.colors.text
        ),
        navigationIcon = {
            onNavClick?.let {
                Icon(
                    modifier = Modifier.clickable(onClick = it),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "topAppbar"
                )
            }
        }
    )
}

@Preview
@Composable
private fun KamoTopAppBarPreview() {
    KaKaoTheme {
        KamoTopAppBar(title = "카카오모빌리티 2차 과제 샘플")
    }
}