package kr.co.domain

import kr.co.common.model.EntityWrapper
import kr.co.model.DistanceTime
import kr.co.model.Locations
import kr.co.model.Route

interface LocationRepository {

    suspend fun getLocations(): EntityWrapper<Locations>

    suspend fun getRoutes(origin: String, destination: String): EntityWrapper<List<Route>>

    suspend fun getDistanceWithTime(
        origin: String,
        destination: String,
    ): EntityWrapper<DistanceTime>
}
