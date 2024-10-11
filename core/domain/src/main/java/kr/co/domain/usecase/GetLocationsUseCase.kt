package kr.co.domain.usecase

import kr.co.domain.LocationRepository
import kr.co.domain.model.Locations
import javax.inject.Inject
import javax.inject.Singleton

class GetLocationsUseCase @Inject constructor(
    private val repository: LocationRepository
): SuspendUseCase<Unit, Locations>(){
    override suspend fun build(params: Unit?): Locations {

        return repository.getLocations()
    }
}