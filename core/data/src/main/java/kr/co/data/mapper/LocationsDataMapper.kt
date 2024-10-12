package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.remote.model.response.GetLocationsResponse

internal object LocationsDataMapper : Mapper<GetLocationsResponse, kr.co.model.Locations> {
    override fun convert(left: GetLocationsResponse): kr.co.model.Locations {
        return with(left) {
            kr.co.model.Locations(
                locations = locations.map {
                    kr.co.model.Locations.Location(
                        origin = it.origin,
                        destination = it.destination
                    )
                }
            )
        }
    }

}
