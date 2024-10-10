package kr.co.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetDistanceTimeResponse(

    @SerialName("distance")
    val distance: Int,

    @SerialName("time")
    val time: Int,
)
