package kr.co.remote

import kr.co.common.model.ApiResponse
import kr.co.remote.model.response.GetDistanceTimeResponse
import kr.co.remote.model.response.GetLocationsResponse
import kr.co.remote.model.response.GetRoutesResponse

interface LocationRemoteDataSource {

    suspend fun getLocations(): ApiResponse<GetLocationsResponse>
    suspend fun getRoutes(origin: String, destination: String): ApiResponse<List<GetRoutesResponse>>
    suspend fun getDistanceWithTime(
        origin: String,
        destination: String,
    ): ApiResponse<GetDistanceTimeResponse>
}
