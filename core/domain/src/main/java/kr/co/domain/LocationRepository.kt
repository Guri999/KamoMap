package kr.co.domain

interface LocationRepository {

    suspend fun getLocations(): kr.co.model.Locations

    suspend fun getRoutes(origin: String, destination: String): List<kr.co.model.Route>

    suspend fun getDistanceWithTime(origin: String, destination: String): kr.co.model.DistanceTime
}
