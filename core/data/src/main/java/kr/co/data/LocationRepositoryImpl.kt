package kr.co.data

import kr.co.data.mapper.DistanceTimeDataMapper
import kr.co.data.mapper.LocationsDataMapper
import kr.co.data.mapper.RoutesDataMapper
import kr.co.domain.LocationRepository
import javax.inject.Inject

internal class LocationRepositoryImpl @Inject constructor(
    private val remote: kr.co.remote.LocationRemoteDataSource,
) : LocationRepository {

    override suspend fun getLocations(): kr.co.model.Locations =
        remote.getLocations()
            .let(LocationsDataMapper::convert)

    override suspend fun getRoutes(
        origin: String,
        destination: String,
    ): List<kr.co.model.Route> =
        remote.getRoutes(
            origin = origin,
            destination = destination,
        ).map(RoutesDataMapper::convert)

    override suspend fun getDistanceWithTime(
        origin: String,
        destination: String,
    ): kr.co.model.DistanceTime =
        remote.getDistanceWithTime(
            origin = origin,
            destination = destination,
        ).let(DistanceTimeDataMapper::convert)

}