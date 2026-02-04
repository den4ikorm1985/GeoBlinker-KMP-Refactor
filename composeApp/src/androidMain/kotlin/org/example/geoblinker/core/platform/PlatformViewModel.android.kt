package org.example.geoblinker.core.platform
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
actual abstract class PlatformViewModel : ViewModel() {
    actual val coroutineScope: CoroutineScope get() = viewModelScope
    actual open fun onCleared() { super.onCleared() }
}
