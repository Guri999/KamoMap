package kr.co.domain.usecase

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kr.co.common.cache.LruCache
import kr.co.common.model.EntityWrapper

abstract class CachedUseCase<PARAMS, RESULT>(
    cacheSize: Int,
    cacheDuration: Long = 12 * 60 * 60 * 1000L,
) : SuspendUseCase<PARAMS, RESULT>() {
    private val cache = LruCache<EntityWrapper<RESULT>>(cacheSize, cacheDuration)

    private val mutex = Mutex()

    override suspend operator fun invoke(params: PARAMS?): EntityWrapper<RESULT> =
        mutex.withLock {
            val key = params?.toString() ?: this::class
            cache.getValue(key) ?: build(params).also {
                if (it is EntityWrapper.Success<RESULT>) {
                    cache.putValue(key, it)
                }
            }
        }
}