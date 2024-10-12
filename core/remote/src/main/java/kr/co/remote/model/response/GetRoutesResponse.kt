package kr.co.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.co.remote.model.type.TrafficStateRemoteType
import kr.co.remote.serializer.PointSerializer

@Serializable
internal data class GetRoutesResponse(

    @Serializable(with = PointSerializer::class)
    @SerialName("points")
    val points: List<Point>,

    @SerialName("traffic_state")
    val trafficState: TrafficStateRemoteType,
) {
    @Serializable
    data class Point(
        val latitude: Double,
        val longitude: Double,
    )
}