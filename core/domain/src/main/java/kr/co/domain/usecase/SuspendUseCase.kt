package kr.co.domain.usecase

abstract class SuspendUseCase<PARAMS, RESULT> {

    protected abstract suspend fun build(params: PARAMS? = null): RESULT

    open suspend operator fun invoke(params: PARAMS? = null) = build(params)
}