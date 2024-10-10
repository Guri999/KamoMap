package kr.co.data

import kr.co.data.mapper.LocationsDataMapper
import kr.co.data.mapper.RoutesDataMapper
import kr.co.data.model.DistanceTimeDataMapper
import kr.co.data.source.remote.LocationRemoteDataSource
import kr.co.domain.LocationRepository
import kr.co.domain.model.DistanceTime
import kr.co.domain.model.Locations
import kr.co.domain.model.Route
import javax.inject.Inject

internal class LocationRepositoryImpl @Inject constructor(
    private val remote: LocationRemoteDataSource
) : LocationRepository {

    override suspend fun getLocations(): Locations =
        remote.getLocations()
            .let(LocationsDataMapper::convert)

    override suspend fun getRoutes(
        origin: String,
        destination: String,
    ): List<Route> =
        remote.getRoutes(
            origin = origin,
            destination = destination,
        ).map(RoutesDataMapper::convert)

    override suspend fun getDistanceWithTime(
        origin: String,
        destination: String,
    ): DistanceTime =
        remote.getDistanceWithTime(
            origin = origin,
            destination = destination,
        ).let(DistanceTimeDataMapper::convert)

}