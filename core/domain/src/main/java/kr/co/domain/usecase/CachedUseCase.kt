package kr.co.domain.usecase

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kr.co.common.cache.LruCache

abstract class CachedUseCase<PARAMS, RESULT>(
    cacheSize: Int,
    cacheDuration: Long = 12 * 60 * 60 * 1000L
): SuspendUseCase<PARAMS, RESULT>() {
    private val cache = LruCache<RESULT>(cacheSize, cacheDuration)

    private val mutex = Mutex()
    @Volatile
    private var isExecuting = false

    override suspend operator fun invoke(params: PARAMS?): RESULT =
        mutex.withLock {
            if (isExecuting) throw IllegalStateException("Already executing")
            isExecuting = true

            try {
                val key = params?.toString() ?: this::class
                cache.getValue(key) ?: build(params).also {
                    cache.putValue(key, it)
                }
            } finally {
                isExecuting = false
            }
        }
}