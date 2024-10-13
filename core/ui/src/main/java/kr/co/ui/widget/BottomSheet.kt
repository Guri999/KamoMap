package kr.co.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.kamo.core.ui.R
import kr.co.ui.theme.KamoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KamoErrorBottomSheet(
    message: String,
    start: String? = null,
    end: String? = null,
    code: Int? = null,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
) {
    ModalBottomSheet(
        modifier = Modifier
            .wrapContentHeight(),
        containerColor = KamoTheme.colors.white,
        tonalElevation = 0.dp,
        contentColor = KamoTheme.colors.black,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = if(code == 4041) "경로조회 실패" else "오류가 발생했습니다.",
                style = KamoTheme.typography.title1Sb,
                color = KamoTheme.colors.black,
            )

            Spacer(Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (start != null && end != null) {
                    Text(
                        text = stringResource(R.string.occurrence_location, start, end),
                        style = KamoTheme.typography.body1R,
                        color = KamoTheme.colors.black
                    )
                }
                if (code != null) {
                    Text(
                        text = stringResource(R.string.error_code, code),
                        style = KamoTheme.typography.body1R,
                        color = KamoTheme.colors.black
                    )
                }
                Text(
                    text = stringResource(R.string.error_message, message),
                    style = KamoTheme.typography.body1R,
                    color = KamoTheme.colors.black
                )
            }

            Spacer(Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(
                    containerColor = KamoTheme.colors.keyColor
                )
            ) {
                Text(
                    text = stringResource(R.string.confirm_button_text),
                    style = KamoTheme.typography.body1R,
                    color = KamoTheme.colors.black
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun KamoErrorBottomSheetPreview() {
    KamoTheme {
        KamoErrorBottomSheet(
            start = "에버랜드",
            end = "서울랜드",
            code = 4041,
            message = "not_found",
            onDismissRequest = {}
        )
    }
}