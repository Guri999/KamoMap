package kr.co.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kr.co.common.model.KamoException
import kr.co.kamo.core.ui.BuildConfig
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<STATE : BaseViewModel.State> : ViewModel() {

    private val logTag by lazy(LazyThreadSafetyMode.PUBLICATION) {
        (this::class.java.simpleName).let { tag: String ->
            tag.ifEmpty { "Unknown_ViewModel" }
        }
    }

    private val initState by lazy {
        createInitialState()
    }

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initState)
    val state = _state.asStateFlow()

    private val _error: MutableSharedFlow<KamoException> = MutableSharedFlow(1)
    val error = _error.asSharedFlow()

    private val _unknownError = MutableSharedFlow<String>(replay = 1)
    val unknownError = _unknownError.asSharedFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    protected val ceh = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            if (throwable is KamoException) {
                _error.emit(throwable)
            }
        }
    }

    protected inline fun launch(
        coroutineContext: CoroutineContext = ceh,
        @ViewModelScoped crossinline action: suspend CoroutineScope.() -> Unit,
    ): Job {
        return viewModelScope.launch(coroutineContext) {
            action()
        }
    }

    protected fun launchWithLoading(
        coroutineContext: CoroutineContext = ceh,
        action: suspend CoroutineScope.() -> Unit,
    ): Job {
        return viewModelScope.launch(coroutineContext) {
            setLoading(true).join()
            action(this)
            setLoading(false).join()
        }
    }

    private fun setLoading(isVisible: Boolean) = viewModelScope.launch {
        _isLoading.emit(isVisible)
    }

    protected fun updateState(action: STATE.() -> STATE) {
        try {
            _state.update(action)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun <T> Flow<T>.debugLog(name: String): Flow<T> =
        if (BuildConfig.DEBUG) {
            onEach { Timber.tag(logTag).d("$name: $it") }
        } else this

    protected fun <T> T.debugLog(name: String? = null): T =
        this.also { Timber.tag(logTag).d("${name.orEmpty()} : $this") }

    protected fun <T> Flow<T>.sharedWhileSubscribed(): SharedFlow<T> =
        shareIn(viewModelScope + ceh, started = SharingStarted.WhileSubscribed())

    protected fun setError(error: KamoException) {
        _error.tryEmit(error)
    }

    protected fun setUnknownError(error: String) {
        _unknownError.tryEmit(error)
    }

    protected abstract fun createInitialState(): STATE

    interface State

    private companion object{
        private const val STATE_KEY = "state"
    }
}