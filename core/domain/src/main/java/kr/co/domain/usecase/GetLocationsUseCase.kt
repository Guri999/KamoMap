package kr.co.domain.usecase

import kr.co.common.model.EntityWrapper
import kr.co.domain.LocationRepository
import kr.co.model.Locations
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val repository: LocationRepository,
) : SuspendUseCase<Unit, Locations>() {
    override suspend fun build(params: Unit?): EntityWrapper<Locations> {

        return repository.getLocations()
    }
}