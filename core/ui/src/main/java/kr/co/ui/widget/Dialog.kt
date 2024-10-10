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
import kr.co.ui.theme.KaKaoTheme
import kr.co.ui.theme.KakaoTheme

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
                onClick = onDismissRequest,
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

@Preview
@Composable
private fun Preview() {
    KaKaoTheme {
        UnknownErrorDialog(
            errorLocation = "Unknown API",
            onDismissRequest = {}
        )
    }
}