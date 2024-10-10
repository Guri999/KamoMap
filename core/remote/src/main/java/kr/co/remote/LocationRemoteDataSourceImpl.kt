package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import kr.co.data.model.DistanceTimeData
import kr.co.data.model.LocationsData
import kr.co.data.model.RouteData
import kr.co.data.source.remote.LocationRemoteDataSource
import kr.co.remote.mapper.DistanceTimeRemoteMapper
import kr.co.remote.mapper.LocationsRemoteMapper
import kr.co.remote.mapper.RoutesRemoteMapper
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

    override suspend fun getLocations(): LocationsData =
        client.get(LOCATION_URL)
            .body<GetLocationsResponse>()
            .let(LocationsRemoteMapper::convert)

    override suspend fun getRoutes(
        origin: String,
        destination: String,
    ): List<RouteData> =
        client.get(ROUTE_URL) {
            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
            parameter("origin", origin)
            parameter("destination", destination)
        }.body<List<GetRoutesResponse>>()
            .map(RoutesRemoteMapper::convert)

    override suspend fun getDistanceWithTime(
        origin: String,
        destination: String,
    ): DistanceTimeData =
        client.get(DISTANCE_TIME_URL) {
            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
            parameter("origin", origin)
            parameter("destination", destination)
        }.body<GetDistanceTimeResponse>()
            .let(DistanceTimeRemoteMapper::convert)

}