package kr.co.common.model

sealed class EntityWrapper<T> {
    data class Success<T>(val data: T) : EntityWrapper<T>()
    data class Error<T>(val error: KamoException) : EntityWrapper<T>()
}