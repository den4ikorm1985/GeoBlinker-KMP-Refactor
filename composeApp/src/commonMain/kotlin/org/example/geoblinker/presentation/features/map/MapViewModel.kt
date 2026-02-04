package org.example.geoblinker.presentation.features.map
import org.example.geoblinker.core.base.BaseViewModel
import org.example.geoblinker.util.*
import kotlinx.coroutines.*

class MapViewModel(
    private val distanceCalculator: DistanceCalculatorImpl,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<MapState, MapEvent, MapEffect>(MapState()) {

    override fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.Refresh -> loadPoints()
            is MapEvent.OnLocationUpdated -> {
                updateState { it.copy(userLocation = event.location) }
                recalculateDistances()
            }
            is MapEvent.MarkerClick -> emitEffect(MapEffect.NavigateToDetails(event.id))
        }
    }

    private fun loadPoints() {
        updateState { it.copy(isLoading = true, error = null) }
        coroutineScope.launch { // FIX: Use coroutineScope from BaseViewModel
            try {
                val uiMarkers = withContext(ioDispatcher) {
                    // Имитация данных
                    listOf(
                        MapMarkerUi("1", Location(55.7, 37.6), "Point A", "", "55.7, 37.6"),
                        MapMarkerUi("2", Location(59.9, 30.3), "Point B", "", "59.9, 30.3")
                    )
                }
                updateState { it.copy(markers = uiMarkers, isLoading = false) }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                updateState { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun recalculateDistances() {
        val userLoc = currentState.userLocation ?: return
        coroutineScope.launch {
            runCatching { // FIX: Safety for GPS stream
                val updated = withContext(ioDispatcher) {
                    currentState.markers.map { marker ->
                        val dist = distanceCalculator.calculateDistance(
                            userLoc.latitude, userLoc.longitude,
                            marker.position.latitude, marker.position.longitude
                        )
                        marker.copy(distanceText = distanceCalculator.formatDistance(dist))
                    }
                }
                updateState { it.copy(markers = updated) }
            }.onFailure { e ->
                if (e is CancellationException) throw e
                // Логируем ошибку, но не валим приложение
            }
        }
    }
}
