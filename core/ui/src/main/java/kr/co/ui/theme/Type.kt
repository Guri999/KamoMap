package kr.co.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kr.co.kamo.core.ui.R

internal val KakaoFontFamily = FontFamily(
    Font(R.font.pretendard_regular, weight = FontWeight.Normal),
    Font(R.font.pretendard_semibold, weight = FontWeight.SemiBold),
    Font(R.font.pretendard_bold, weight = FontWeight.Bold),
)

private val Display1B = TextStyle(
    fontFamily = KakaoFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 40.sp,
    lineHeight = 52.sp,
)

private val Display2SB = Display1B.copy(fontWeight = FontWeight.SemiBold)

private val Title1SB = Display2SB.copy(fontSize = 24.sp, lineHeight = 32.sp)
private val Title2SB = Title1SB.copy(fontSize = 20.sp, lineHeight = 28.sp)

private val Body1SB = Title2SB.copy(fontSize = 16.sp, lineHeight = 24.sp)
private val Body1R = Body1SB.copy(fontWeight = FontWeight.Normal)
private val Body2SB = Body1SB.copy(fontSize = 14.sp, lineHeight = 20.sp)
private val Body2R = Body2SB.copy(fontWeight = FontWeight.Normal)

internal val Typography = KakaoTypography()

@Immutable
data class KakaoTypography(
    val display1B: TextStyle = Display1B,
    val display2Sb: TextStyle = Display2SB,
    val title1Sb: TextStyle = Title1SB,
    val title2Sb: TextStyle = Title2SB,
    val body1Sb: TextStyle = Body1SB,
    val body1R: TextStyle = Body1R,
    val body2Sb: TextStyle = Body1SB,
    val body2R: TextStyle = Body2R,
)

internal val LocalKakaoTypography = staticCompositionLocalOf { KakaoTypography() }