package kr.co.data.mapper

import kr.co.common.mapper.ApiMapper
import kr.co.model.DistanceTime
import kr.co.remote.model.response.GetDistanceTimeResponse

internal object DistanceTimeDataMapper : ApiMapper<GetDistanceTimeResponse, DistanceTime>() {
    override fun convert(left: GetDistanceTimeResponse): DistanceTime {
        return with(left) {
            DistanceTime(
                distance = distance,
                time = time,
            )
        }
    }

}
