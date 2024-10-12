package kr.co.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kr.co.ui.theme.KamoTheme

@Composable
fun UnknownErrorDialog(
    errorLocation: String,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(KamoTheme.colors.white)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "에러가 발생했습니다.",
                style = KamoTheme.typography.title2Sb,
                color = KamoTheme.colors.black,
            )

            Text(
                text = "발생 위치: $errorLocation",
                style = KamoTheme.typography.body2R,
                color = KamoTheme.colors.stroke
            )

            TextButton (
                modifier = Modifier.fillMaxWidth(),
                onClick = onDismissRequest,
            ) {
                Text(
                    text = "확인",
                    style = KamoTheme.typography.body1Sb,
                    color = KamoTheme.colors.keyColor
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    KamoTheme {
        UnknownErrorDialog(
            errorLocation = "Unknown API",
            onDismissRequest = {}
        )
    }
}