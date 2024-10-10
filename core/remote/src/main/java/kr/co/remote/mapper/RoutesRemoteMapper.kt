package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.RouteData
import kr.co.remote.model.response.GetRoutesResponse

internal object RoutesRemoteMapper : Mapper<GetRoutesResponse, RouteData> {
    override fun convert(left: GetRoutesResponse): RouteData {
        return with(left) {
            RouteData(
                points = points.map {
                    RouteData.Point(
                        latitude = it.latitude,
                        longitude = it.longitude
                    )
                },
                trafficState = trafficState.let(RouteStateTypeRemoteMapper::convert)
            )
        }
    }

}