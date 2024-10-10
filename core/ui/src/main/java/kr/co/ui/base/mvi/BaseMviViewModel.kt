package kr.co.ui.base.mvi

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

abstract class BaseMviViewModel<
        I : MviIntent,
        S : MviViewState,
        E : MviSingleEvent,
        > : ViewModel(), MviViewModel<I, S, E> {

    protected val ceh = CoroutineExceptionHandler { _, throwable ->
        Timber.tag(logTag).e(throwable)
    }

    private val logTag by lazy(LazyThreadSafetyMode.PUBLICATION) {
        (this::class.java.simpleName).let { tag: String ->
            tag.ifEmpty { "Unknown_ViewModel" }
        }
    }

    private val eventChannel = Channel<E>(Channel.UNLIMITED)

    private val _intentFlow =
        MutableSharedFlow<I>(extraBufferCapacity = 64)

    protected val intentFlow = _intentFlow.asSharedFlow()

    override val singleEvent: Flow<E> = eventChannel.receiveAsFlow()

    override suspend fun processIntent(intent: I) = _intentFlow.emit(intent)

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        eventChannel.close()
    }


}