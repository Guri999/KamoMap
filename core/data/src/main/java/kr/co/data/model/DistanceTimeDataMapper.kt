package kr.co.data.model

import kr.co.common.mapper.Mapper
import kr.co.domain.model.DistanceTime

internal object DistanceTimeDataMapper : Mapper<DistanceTimeData, DistanceTime> {
    override fun convert(left: DistanceTimeData): DistanceTime {
        return with(left) {
            DistanceTime(
                distance = distance,
                time = time,
            )
        }
    }

}
