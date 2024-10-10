package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.LocationsData
import kr.co.remote.model.response.GetLocationsResponse

internal object LocationsRemoteMapper : Mapper<GetLocationsResponse, LocationsData> {
    override fun convert(left: GetLocationsResponse): LocationsData {
        return with(left) {
            LocationsData(
                locations = locations.map {
                    LocationsData.Location(
                        origin = it.origin,
                        destination = it.destination
                    )
                }
            )
        }
    }
}