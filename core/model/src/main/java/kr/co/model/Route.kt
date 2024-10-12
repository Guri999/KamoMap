package kr.co.model

import kr.co.model.type.TrafficStateType

data class Route(
    val points: List<Point>,
    val trafficState: TrafficStateType,
) {
    data class Point(
        val latitude: Double,
        val longitude: Double,
    )
}
