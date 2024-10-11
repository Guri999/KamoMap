package kr.co.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

private val Black = Color(0xFF1B1C1E)
private val White = Color(0xFFFFFFFF)

private val Grey1 = Color(0xFF464646)
private val Grey2 = Color(0xFF6B6B6B)
private val Grey3 = Color(0xFF909090)
private val Grey4 = Color(0xFFB5B5B5)
private val Grey5 = Color(0xFFDADADA)
private val Grey6 = Color(0xFFEEEEEE)
private val Grey7 = Color(0xFFF8F8F8)

val KeyColor = Color(0xFFFFDE2F)
private val KeyColor2 = Color(0x99FFDE2F)
private val KeyColor3 = Color(0x33FFDE2F)

private val Red1 = Color(0x79E67F7E)
private val Red2 = Color(0xFFFD382A)
private val Red3 = Color(0xFFFD382A)

private val Orange = Color(0xFFF7AB17)
private val Yellow = Color(0xFFF0D300)
private val Green = Color(0xFF3BD72C)
private val Blue = Color(0xFF0096FF)

private val Error = Color(0xFFF24B4E)

private val Okay = Color(0xFF0BB26C)

val RouteUnknown: Int = Red1.toArgb()
val RouteBlock: Int = Red2.toArgb()
val RouteJam: Int = Red3.toArgb()
val RouteDelay: Int = Orange.toArgb()
val RouteSlow: Int = Yellow.toArgb()
val RouteNormal: Int = Green.toArgb()

@Immutable
data class ColorSet(
    val material: ColorScheme,
    val keyColor: Color = KeyColor,
    val bg: Color,
    val text: Color,
    val button: Color,
    val emphatic: Color,
    val emphatic2: Color,
    val hintText: Color,
    val topAppbar: Color,
    val stroke: Color,
    val error: Color,
    val okay: Color,
)

internal val lightColorSet = ColorSet(
    material = lightColorScheme(),
    bg = White,
    text = Black,
    button = Blue,
    emphatic = Green,
    emphatic2 = Blue,
    hintText = Grey3,
    topAppbar = Grey4,
    stroke = Grey4,
    error = Error,
    okay = Okay,
)

internal val darkColorSet = ColorSet(
    material = darkColorScheme(),
    bg = Black,
    text = White,
    button = Blue,
    emphatic = Green,
    emphatic2 = Blue,
    hintText = Grey5,
    topAppbar = Grey2,
    stroke = Grey6,
    error = Error,
    okay = Okay,
)

internal val LocalColorSet = androidx.compose.runtime.compositionLocalOf { lightColorSet }