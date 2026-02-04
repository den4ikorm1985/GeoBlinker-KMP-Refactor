package org.example.geoblinker.core.base
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.geoblinker.core.platform.PlatformViewModel
abstract class BaseViewModel<S : ViewState, E : ViewEvent, F : ViewEffect>(initialState: S) : PlatformViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()
    private val _effects = MutableSharedFlow<F>(replay = 0, extraBufferCapacity = 64, onBufferOverflow = BufferOverflow.SUSPEND)
    val effects: SharedFlow<F> = _effects.asSharedFlow()
    protected val currentState: S get() = _state.value
    abstract fun onEvent(event: E)
    protected fun updateState(transform: (S) -> S) { runCatching { _state.update { transform(it) } } }
    protected fun emitEffect(effect: F) { coroutineScope.launch { runCatching { _effects.emit(effect) } } }
}
