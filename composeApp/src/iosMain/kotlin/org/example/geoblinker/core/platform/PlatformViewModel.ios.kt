package org.example.geoblinker.core.platform
import kotlinx.coroutines.*
actual abstract class PlatformViewModel {
    actual val coroutineScope: CoroutineScope = MainScope()
    actual open fun onCleared() { coroutineScope.cancel() }
}
