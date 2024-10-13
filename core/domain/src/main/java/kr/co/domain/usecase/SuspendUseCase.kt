package kr.co.domain.usecase

import kr.co.common.model.EntityWrapper

abstract class SuspendUseCase<PARAMS, RESULT> {

    protected abstract suspend fun build(params: PARAMS? = null): EntityWrapper<RESULT>

    open suspend operator fun invoke(params: PARAMS? = null) = build(params)
}