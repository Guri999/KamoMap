package kr.co.data

import kr.co.common.model.EntityWrapper
import kr.co.data.mapper.DistanceTimeDataMapper
import kr.co.data.mapper.LocationsDataMapper
import kr.co.data.mapper.RoutesDataMapper
import kr.co.domain.LocationRepository
import kr.co.model.DistanceTime
import kr.co.model.Locations
import kr.co.model.Route
import javax.inject.Inject

internal class LocationRepositoryImpl @Inject constructor(
    private val remote: kr.co.remote.LocationRemoteDataSource,
) : LocationRepository {

    override suspend fun getLocations(): EntityWrapper<Locations> =
        remote.getLocations()
            .let(LocationsDataMapper::convertResponse)

    override suspend fun getRoutes(
        origin: String,
        destination: String,
    ): EntityWrapper<List<Route>> =
        remote.getRoutes(
            origin = origin,
            destination = destination,
        ).let(RoutesDataMapper::convertResponse)

    override suspend fun getDistanceWithTime(
        origin: String,
        destination: String,
    ): EntityWrapper<DistanceTime> =
        remote.getDistanceWithTime(
            origin = origin,
            destination = destination,
        ).let(DistanceTimeDataMapper::convertResponse)

}