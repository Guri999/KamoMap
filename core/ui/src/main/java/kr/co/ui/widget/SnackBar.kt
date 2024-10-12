package kr.co.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.KamoIcon
import kr.co.ui.icon.kamoicon.Error
import kr.co.ui.theme.KamoTheme

@Composable
fun KamoSnackBarHost(
    snackBarHostState: SnackbarHostState,
) {
    SnackbarHost(
        hostState = snackBarHostState
    ) {
        KamoErrorSnackBar(
            message = it.visuals.message
        )
    }
}

@Composable
private fun KamoErrorSnackBar(
    message: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .alpha(0.8f)
            .clip(RoundedCornerShape(12.dp))
            .background(KamoTheme.colors.black)
            .padding(
                vertical = 20.dp,
                horizontal = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = KamoIcon.Error,
            contentDescription = "error",
            tint = Color.Unspecified
        )
        Text(
            text = message,
            style = KamoTheme.typography.body1R,
            color = KamoTheme.colors.white
        )
    }
}

@Preview
@Composable
private fun Preview() {
    KamoTheme {
        Column(
            Modifier.background(Color.White)
        ) {
            KamoErrorSnackBar(
                message = "경로조회 실패"
            )
        }
    }
}