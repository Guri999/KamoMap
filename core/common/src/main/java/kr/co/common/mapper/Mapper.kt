package kr.co.common.mapper

interface Mapper<LEFT, RIGHT> {
    fun convert(left: LEFT): RIGHT
}