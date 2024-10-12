package kr.co.data.mapper.type

import kr.co.common.mapper.Mapper
import kr.co.remote.model.type.TrafficStateRemoteType

internal object RouteStateTypeDataMapper :
    Mapper<TrafficStateRemoteType, kr.co.model.type.TrafficStateType> {
    override fun convert(left: TrafficStateRemoteType): kr.co.model.type.TrafficStateType {
        return when (left) {
            TrafficStateRemoteType.UNKNOWN -> kr.co.model.type.TrafficStateType.UNKNOWN
            TrafficStateRemoteType.JAM -> kr.co.model.type.TrafficStateType.JAM
            TrafficStateRemoteType.DELAY -> kr.co.model.type.TrafficStateType.DELAY
            TrafficStateRemoteType.SLOW -> kr.co.model.type.TrafficStateType.SLOW
            TrafficStateRemoteType.NORMAL -> kr.co.model.type.TrafficStateType.NORMAL
            TrafficStateRemoteType.BLOCK -> kr.co.model.type.TrafficStateType.BLOCK
        }
    }

}
