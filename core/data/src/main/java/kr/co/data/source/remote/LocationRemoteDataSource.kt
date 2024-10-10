package kr.co.data.source.remote

import kr.co.data.model.DistanceTimeData
import kr.co.data.model.LocationsData
import kr.co.data.model.RouteData

interface LocationRemoteDataSource {

    suspend fun getLocations(): LocationsData
    suspend fun getRoutes(origin: String, destination: String): List<RouteData>
    suspend fun getDistanceWithTime(origin: String, destination: String): DistanceTimeData
}
