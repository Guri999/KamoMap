package kr.co.domain.usecase.location

import kr.co.common.model.EntityWrapper
import kr.co.domain.LocationRepository
import kr.co.domain.usecase.CachedUseCase
import kr.co.model.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRoutesUseCase @Inject constructor(
    private val repository: LocationRepository,
) : CachedUseCase<GetRoutesUseCase.Params, List<Route>>(10) {

    override suspend fun build(params: Params?): EntityWrapper<List<Route>> {
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