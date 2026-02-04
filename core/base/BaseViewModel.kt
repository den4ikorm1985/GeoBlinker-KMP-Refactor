package org.example.geoblinker.core.base

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<S : ViewState, E : ViewEvent, F : ViewEffect>(
    initialState: S
) : PlatformViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    
    // Эффекты с буфером (Исправлено по аудиту)
    private val _effects = MutableSharedFlow<F>(replay = 0, extraBufferCapacity = 64)
    val effects = _effects.asSharedFlow()

    protected val currentState: S get() = _state.value
    abstract fun onEvent(event: E)
    
    protected fun updateState(transform: (S) -> S) {
        _state.update { transform(it) }
    }

    protected fun emitEffect(effect: F) {
        coroutineScope.launch { _effects.emit(effect) }
    }
}
