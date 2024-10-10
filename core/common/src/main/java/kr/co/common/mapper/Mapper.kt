package kr.co.common.mapper

fun interface Mapper<LEFT,RIGHT> {
    fun convert(left: LEFT): RIGHT
}