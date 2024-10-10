package kr.co.domain.usecase

import kr.co.domain.LocationRepository
import kr.co.domain.model.DistanceTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDistanceTimeUseCase @Inject constructor(
    private val repository: LocationRepository
): SuspendUseCase<GetDistanceTimeUseCase.Params, DistanceTime>() {

    override suspend fun build(params: Params?): DistanceTime {
        checkNotNull(params)

        return params.run {
            repository.getDistanceWithTime(
                origin = origin,
                destination = destination,
            )
        }
    }

    data class Params(
        val origin: String,
        val destination: String,
    )
}