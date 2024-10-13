package kr.co.data.mapper

import kr.co.common.mapper.ApiMapper
import kr.co.data.mapper.type.RouteStateTypeDataMapper
import kr.co.model.Route
import kr.co.remote.model.response.GetRoutesResponse

internal object RoutesDataMapper : ApiMapper<List<GetRoutesResponse>, List<Route>>() {
    override fun convert(left: List<GetRoutesResponse>): List<Route> {
        return with(left) {
            map { response ->
                Route(
                    points = response.points.map {
                        Route.Point(
                            latitude = it.latitude,
                            longitude = it.longitude
                        )
                    },
                    trafficState = response.trafficState.let(RouteStateTypeDataMapper::convert),
                )
            }
        }
    }

}
