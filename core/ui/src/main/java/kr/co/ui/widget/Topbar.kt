package kr.co.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kr.co.ui.theme.KamoTheme

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
                style = KamoTheme.typography.body1Sb,
                color = KamoTheme.colors.text
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = KamoTheme.colors.topAppbar,
            titleContentColor = KamoTheme.colors.text
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
    KamoTheme {
        KamoTopAppBar(title = "카카오모빌리티 2차 과제 샘플")
    }
}