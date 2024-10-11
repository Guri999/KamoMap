package kr.co.common.ext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kr.co.kamo.core.common.BuildConfig
import timber.log.Timber

fun <T> Flow<T>.debugLog(name: String): Flow<T> =
    if (BuildConfig.DEBUG) {
        onEach { Timber.d("$name: $it") }
    } else this

fun <T> T.debugLog(name: String? = null): T =
    if (BuildConfig.DEBUG) {
        this.also { Timber.d("${name.orEmpty()} : $this") }
    } else this