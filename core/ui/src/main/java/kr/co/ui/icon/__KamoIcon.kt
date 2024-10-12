package kr.co.ui.icon

import androidx.compose.ui.graphics.vector.ImageVector
import kr.co.ui.icon.kamoicon.Car
import kr.co.ui.icon.kamoicon.Error
import kotlin.collections.List as ____KtList

public object KamoIcon

private var __AllIcons: ____KtList<ImageVector>? = null

public val KamoIcon.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(Car, Error)
        return __AllIcons!!
    }
