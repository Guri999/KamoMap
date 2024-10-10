package kr.co.common.util

import java.util.Locale

fun timeFormat(second: Int): String {
    val sec = second % 60
    val min = (second / 60) % 60
    val hour = second / 3600

    return String.format(Locale.getDefault(),"%02d:%02d", hour, min)
}