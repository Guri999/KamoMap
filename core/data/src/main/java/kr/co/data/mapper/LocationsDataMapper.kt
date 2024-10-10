package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.LocationsData
import kr.co.domain.model.Locations

internal object LocationsDataMapper : Mapper<LocationsData, Locations> {
    override fun convert(left: LocationsData): Locations {
        return with(left) {
            Locations(
                locations = locations.map {
                    Locations.Location(
                        origin = it.origin,
                        destination = it.destination
                    )
                }
            )
        }
    }

}
