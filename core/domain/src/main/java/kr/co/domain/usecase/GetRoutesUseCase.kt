package kr.co.domain.usecase

import kr.co.domain.LocationRepository
import kr.co.domain.model.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRoutesUseCase @Inject constructor(
    private val repository: LocationRepository
): CachedUseCase<GetRoutesUseCase.Params, List<Route>>(10) {

    override suspend fun build(params: Params?): List<Route> {
        checkNotNull(params)

        return params.run {
            repository.getRoutes(
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