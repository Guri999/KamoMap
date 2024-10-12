package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.mapper.type.RouteStateTypeDataMapper
import kr.co.remote.model.response.GetRoutesResponse

internal object RoutesDataMapper : Mapper<GetRoutesResponse, kr.co.model.Route> {
    override fun convert(left: GetRoutesResponse): kr.co.model.Route {
        return with(left) {
            kr.co.model.Route(
                points = points.map {
                    kr.co.model.Route.Point(
                        latitude = it.latitude,
                        longitude = it.longitude
                    )
                },
                trafficState = trafficState.let(RouteStateTypeDataMapper::convert),
            )
        }
    }

}
