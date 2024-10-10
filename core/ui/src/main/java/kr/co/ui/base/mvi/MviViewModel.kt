package kr.co.ui.base.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MviViewModel<
        I : MviIntent,
        S : MviViewState,
        E : MviSingleEvent,
        > {
    val viewState: StateFlow<S>

    val singleEvent: Flow<E>

    suspend fun processIntent(intent: I)
}