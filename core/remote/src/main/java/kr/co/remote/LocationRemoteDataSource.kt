package kr.co.remote

import kr.co.remote.model.response.GetDistanceTimeResponse
import kr.co.remote.model.response.GetLocationsResponse
import kr.co.remote.model.response.GetRoutesResponse

interface LocationRemoteDataSource {

    suspend fun getLocations(): GetLocationsResponse
    suspend fun getRoutes(origin: String, destination: String): List<GetRoutesResponse>
    suspend fun getDistanceWithTime(origin: String, destination: String): GetDistanceTimeResponse
}
