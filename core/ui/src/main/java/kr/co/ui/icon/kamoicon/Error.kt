package kr.co.ui.icon.kamoicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.KamoIcon

public val KamoIcon.Error: ImageVector
    get() {
        if (_error != null) {
            return _error!!
        }
        _error = Builder(
            name = "Error", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFF24B4E)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 12.0f)
                moveToRelative(-12.0f, 0.0f)
                arcToRelative(12.0f, 12.0f, 0.0f, true, true, 24.0f, 0.0f)
                arcToRelative(12.0f, 12.0f, 0.0f, true, true, -24.0f, 0.0f)
            }
            path(
                fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(7.9333f, 16.3333f)
                lineTo(7.0f, 15.4f)
                lineTo(10.7333f, 11.6667f)
                lineTo(7.0f, 7.9333f)
                lineTo(7.9333f, 7.0f)
                lineTo(11.6667f, 10.7333f)
                lineTo(15.4f, 7.0f)
                lineTo(16.3333f, 7.9333f)
                lineTo(12.6f, 11.6667f)
                lineTo(16.3333f, 15.4f)
                lineTo(15.4f, 16.3333f)
                lineTo(11.6667f, 12.6f)
                lineTo(7.9333f, 16.3333f)
                close()
            }
        }
            .build()
        return _error!!
    }

private var _error: ImageVector? = null
