package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.remote.model.response.GetDistanceTimeResponse

internal object DistanceTimeDataMapper : Mapper<GetDistanceTimeResponse, kr.co.model.DistanceTime> {
    override fun convert(left: GetDistanceTimeResponse): kr.co.model.DistanceTime {
        return with(left) {
            kr.co.model.DistanceTime(
                distance = distance,
                time = time,
            )
        }
    }

}
