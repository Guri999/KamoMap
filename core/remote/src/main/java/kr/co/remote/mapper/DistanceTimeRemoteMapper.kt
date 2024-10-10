package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.DistanceTimeData
import kr.co.remote.model.response.GetDistanceTimeResponse

internal object DistanceTimeRemoteMapper : Mapper<GetDistanceTimeResponse, DistanceTimeData>{
    override fun convert(left: GetDistanceTimeResponse): DistanceTimeData =
        DistanceTimeData(
            distance = left.distance,
            time = left.time,
        )
}
