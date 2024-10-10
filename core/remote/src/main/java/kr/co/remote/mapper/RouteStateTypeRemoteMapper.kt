package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.type.TrafficStateDataType
import kr.co.remote.model.type.TrafficStateRemoteType

internal object RouteStateTypeRemoteMapper : Mapper<TrafficStateRemoteType, TrafficStateDataType> {
    override fun convert(left: TrafficStateRemoteType): TrafficStateDataType {
        return when(left) {
            TrafficStateRemoteType.UNKNOWN -> TrafficStateDataType.UNKNOWN
            TrafficStateRemoteType.JAM -> TrafficStateDataType.JAM
            TrafficStateRemoteType.DELAY -> TrafficStateDataType.DELAY
            TrafficStateRemoteType.SLOW -> TrafficStateDataType.SLOW
            TrafficStateRemoteType.NORMAL -> TrafficStateDataType.NORMAL
            TrafficStateRemoteType.BLOCK -> TrafficStateDataType.BLOCK
        }
    }
}