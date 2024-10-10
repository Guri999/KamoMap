package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.mapper.type.RouteStateTypeDataMapper
import kr.co.data.model.RouteData
import kr.co.domain.model.Route

internal object RoutesDataMapper : Mapper<RouteData, Route> {
    override fun convert(left: RouteData): Route {
        return with(left) {
            Route(
                points = points.map {
                    Route.Point(
                        latitude = it.latitude,
                        longitude = it.longitude
                    )
                },
                trafficState = trafficState.let(RouteStateTypeDataMapper::convert),
            )
        }
    }

}
