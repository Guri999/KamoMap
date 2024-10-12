package kr.co.ui.icon.kamoicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.KamoIcon

public val KamoIcon.Car: ImageVector
    get() {
        if (_car != null) {
            return _car!!
        }
        _car = Builder(
            name = "Car", defaultWidth = 56.0.dp, defaultHeight = 56.0.dp, viewportWidth
            = 56.0f, viewportHeight = 56.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFFDE2F)), stroke = null, fillAlpha = 0.2f,
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(28.0f, 28.0f)
                moveToRelative(-28.0f, 0.0f)
                arcToRelative(28.0f, 28.0f, 0.0f, true, true, 56.0f, 0.0f)
                arcToRelative(28.0f, 28.0f, 0.0f, true, true, -56.0f, 0.0f)
            }
            path(
                fill = SolidColor(Color(0xFFFFDE2F)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(19.0202f, 12.8333f)
                horizontalLineTo(28.0f)
                verticalLineTo(14.7425f)
                horizontalLineTo(21.2935f)
                curveTo(20.3337f, 14.7425f, 19.5092f, 15.4243f, 19.3291f, 16.367f)
                lineTo(18.0867f, 22.867f)
                curveTo(17.8511f, 24.0998f, 18.796f, 25.2425f, 20.0511f, 25.2425f)
                horizontalLineTo(28.0f)
                verticalLineTo(39.5607f)
                horizontalLineTo(19.303f)
                verticalLineTo(41.9015f)
                curveTo(19.303f, 42.4538f, 18.8553f, 42.9015f, 18.303f, 42.9015f)
                horizontalLineTo(16.4849f)
                curveTo(15.9326f, 42.9015f, 15.4849f, 42.4538f, 15.4849f, 41.9015f)
                verticalLineTo(39.5607f)
                horizontalLineTo(13.6667f)
                curveTo(12.5621f, 39.5607f, 11.6667f, 38.6653f, 11.6667f, 37.5607f)
                verticalLineTo(27.2425f)
                curveTo(11.6667f, 26.138f, 12.5621f, 25.2425f, 13.6667f, 25.2425f)
                horizontalLineTo(14.8087f)
                lineTo(17.0622f, 14.4254f)
                curveTo(17.2555f, 13.498f, 18.0729f, 12.8333f, 19.0202f, 12.8333f)
                close()
                moveTo(15.9622f, 28.1516f)
                curveTo(15.9622f, 27.5993f, 16.4099f, 27.1516f, 16.9622f, 27.1516f)
                horizontalLineTo(17.8258f)
                curveTo(18.3781f, 27.1516f, 18.8258f, 27.5993f, 18.8258f, 28.1516f)
                verticalLineTo(29.0152f)
                curveTo(18.8258f, 29.5675f, 18.3781f, 30.0152f, 17.8258f, 30.0152f)
                horizontalLineTo(16.9622f)
                curveTo(16.4099f, 30.0152f, 15.9622f, 29.5675f, 15.9622f, 29.0152f)
                verticalLineTo(28.1516f)
                close()
                moveTo(36.9798f, 12.8333f)
                horizontalLineTo(28.0f)
                verticalLineTo(14.7425f)
                horizontalLineTo(34.7065f)
                curveTo(35.6663f, 14.7425f, 36.4908f, 15.4243f, 36.671f, 16.367f)
                lineTo(37.9133f, 22.867f)
                curveTo(38.149f, 24.0998f, 37.204f, 25.2425f, 35.9489f, 25.2425f)
                horizontalLineTo(28.0f)
                verticalLineTo(39.5607f)
                horizontalLineTo(36.697f)
                verticalLineTo(41.9015f)
                curveTo(36.697f, 42.4538f, 37.1447f, 42.9015f, 37.697f, 42.9015f)
                horizontalLineTo(39.5152f)
                curveTo(40.0674f, 42.9015f, 40.5152f, 42.4538f, 40.5152f, 41.9015f)
                verticalLineTo(39.5607f)
                horizontalLineTo(42.3333f)
                curveTo(43.4379f, 39.5607f, 44.3333f, 38.6653f, 44.3333f, 37.5607f)
                verticalLineTo(27.2426f)
                curveTo(44.3333f, 26.138f, 43.4379f, 25.2426f, 42.3333f, 25.2426f)
                horizontalLineTo(41.1913f)
                lineTo(38.9378f, 14.4254f)
                curveTo(38.7445f, 13.498f, 37.9272f, 12.8333f, 36.9798f, 12.8333f)
                close()
                moveTo(40.0379f, 28.1516f)
                curveTo(40.0379f, 27.5993f, 39.5902f, 27.1516f, 39.0379f, 27.1516f)
                horizontalLineTo(38.1742f)
                curveTo(37.6219f, 27.1516f, 37.1742f, 27.5993f, 37.1742f, 28.1516f)
                verticalLineTo(29.0152f)
                curveTo(37.1742f, 29.5675f, 37.6219f, 30.0152f, 38.1742f, 30.0152f)
                horizontalLineTo(39.0379f)
                curveTo(39.5901f, 30.0152f, 40.0379f, 29.5675f, 40.0379f, 29.0152f)
                verticalLineTo(28.1516f)
                close()
            }
        }
            .build()
        return _car!!
    }

private var _car: ImageVector? = null
