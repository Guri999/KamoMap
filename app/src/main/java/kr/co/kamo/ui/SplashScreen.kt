package kr.co.kamo.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.co.kamo.R
import kr.co.ui.theme.KamoTheme
import kr.co.ui.theme.KeyColor

@Composable
internal fun SplashRoute() {

    var visibility by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visibility = true
    }

    SplashScreen(
        visible = visibility
    )
}

@Composable
private fun SplashScreen(
    visible: Boolean = true
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(
                animationSpec = tween(durationMillis = 600)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(KeyColor)
                    .padding(
                        vertical = 80.dp,
                        horizontal = 40.dp
                    )
            ) {
                Text(
                    text = "KAMO\nMAP",
                    fontFamily = KamoTheme.typography.body1R.fontFamily,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 70.sp
                )
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomEnd),
            visible = visible,
            enter = slideIn(
                animationSpec = tween(
                    durationMillis = 600,
                    delayMillis = 500
                ),
                initialOffset = { IntOffset(it.width, it.height) }
            )
        ) {
            Image(
                modifier = Modifier.align(Alignment.BottomEnd),
                painter = painterResource(R.drawable.lion),
                contentDescription = "Splash Character"
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    KamoTheme {
        SplashRoute()
    }
}