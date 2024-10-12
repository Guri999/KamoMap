package kr.co.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiError(

    @SerialName("code")
    val code: Int,

    @SerialName("message")
    val message: String,
)
