package kr.co.domain

import kr.co.domain.model.DistanceTime
import kr.co.domain.model.Locations
import kr.co.domain.model.Route

interface LocationRepository {

    suspend fun getLocations(): Locations

    suspend fun getRoutes(origin: String, destination: String): List<Route>

    suspend fun getDistanceWithTime(origin: String, destination: String): DistanceTime
}
