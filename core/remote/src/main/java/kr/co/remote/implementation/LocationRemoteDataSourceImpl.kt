package kr.co.remote.implementation

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.HttpHeaders
import kr.co.common.model.ApiResponse
import kr.co.remote.LocationRemoteDataSource
import kr.co.remote.model.ApiError
import kr.co.remote.model.LocationsException
import kr.co.remote.model.response.GetDistanceTimeResponse
import kr.co.remote.model.response.GetLocationsResponse
import kr.co.remote.model.response.GetRoutesResponse
import javax.inject.Inject

internal class LocationRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : LocationRemoteDataSource {

    private companion object {
        const val V1 = "/api/v1/coding-assignment/"
        const val LOCATION_URL = "${V1}locations"
        const val ROUTE_URL = "${V1}routes"
        const val DISTANCE_TIME_URL = "${V1}distance-time"
    }

    override suspend fun getLocations(): ApiResponse<GetLocationsResponse> =
        client.get(LOCATION_URL)
            .wrapResponse<GetLocationsResponse>()

    override suspend fun getRoutes(
        origin: String,
        destination: String,
    ): ApiResponse<List<GetRoutesResponse>> =
        client.get(ROUTE_URL) {
            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
            parameter("origin", origin)
            parameter("destination", destination)
        }.wrapResponse<List<GetRoutesResponse>>()

    override suspend fun getDistanceWithTime(
        origin: String,
        destination: String,
    ): ApiResponse<GetDistanceTimeResponse> =
        client.get(DISTANCE_TIME_URL) {
            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
            parameter("origin", origin)
            parameter("destination", destination)
        }.wrapResponse<GetDistanceTimeResponse>()

    private suspend inline fun <reified T> HttpResponse.wrapResponse() = when (status.value) {
        in 200 until 300 -> ApiResponse.Success(body<T>())


        else -> body<ApiError>().let {
            LocationsException(
                code = it.code,
                message = it.message,
                errorUrl = request.url.toString(),
            )
        }.let { ApiResponse.Error(it) }
    }

}