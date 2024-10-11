package kr.co.common.ext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

fun <T> Flow<T>.startWith(item: T): Flow<T> = flow {
    emitAll(flowOf(item))
    emitAll(this@startWith)
}