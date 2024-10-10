package kr.co.data.mapper.type

import kr.co.common.mapper.Mapper
import kr.co.data.model.type.TrafficStateDataType
import kr.co.domain.model.type.TrafficStateType

internal object RouteStateTypeDataMapper : Mapper<TrafficStateDataType, TrafficStateType>{
    override fun convert(left: TrafficStateDataType): TrafficStateType {
        return when(left) {
            TrafficStateDataType.UNKNOWN -> TrafficStateType.UNKNOWN
            TrafficStateDataType.JAM -> TrafficStateType.JAM
            TrafficStateDataType.DELAY -> TrafficStateType.DELAY
            TrafficStateDataType.SLOW -> TrafficStateType.SLOW
            TrafficStateDataType.NORMAL -> TrafficStateType.NORMAL
            TrafficStateDataType.BLOCK -> TrafficStateType.BLOCK
        }
    }

}
