package kr.co.common.model

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val error: KamoException) : ApiResponse<Nothing>()
}