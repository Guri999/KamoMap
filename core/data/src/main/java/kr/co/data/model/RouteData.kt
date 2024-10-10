package kr.co.data.model

import kr.co.data.model.type.TrafficStateDataType

data class RouteData(
    val points: List<Point>,
    val trafficState: TrafficStateDataType,
) {
    data class Point(
        val latitude: Double,
        val longitude: Double,
    )
}

