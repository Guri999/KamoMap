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

private val Error = Color(0xFFF24B4E)

private val Red1 = Color(0x79E67F7E)
private val Red2 = Color(0xFFFD382A)
private val Red3 = Color(0xFFFD382A)

private val Orange = Color(0xFFF7AB17)
private val Yellow = Color(0xFFF0D300)
private val Green = Color(0xFF3BD72C)

private val Blue = Color(0xFF0096FF)

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
    val keyColor2: Color = KeyColor2,
    val keyColor3: Color = KeyColor3,
    val black: Color = Black,
    val white: Color = White,
    val grey1: Color = Grey1,
    val grey2: Color = Grey2,
    val grey3: Color = Grey3,
    val grey4: Color = Grey4,
    val grey5: Color = Grey5,
    val grey6: Color = Grey6,
    val grey7: Color = Grey7,
    val stroke: Color,
    val error: Color,
)

internal val lightColorSet = ColorSet(
    material = lightColorScheme(),
    stroke = Grey4,
    error = Error,
)

internal val darkColorSet = ColorSet(
    material = darkColorScheme(),
    stroke = Grey6,
    error = Error,
)

internal val LocalColorSet = androidx.compose.runtime.compositionLocalOf { lightColorSet }