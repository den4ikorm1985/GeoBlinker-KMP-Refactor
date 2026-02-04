package org.example.geoblinker.presentation.features.map
import org.example.geoblinker.core.base.*
import org.example.geoblinker.util.Location

data class MapMarkerUi(
    val id: String,
    val position: Location,
    val title: String,
    val distanceText: String,
    val coordinatesText: String
)

data class MapState(
    val userLocation: Location? = null,
    val markers: List<MapMarkerUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewState

sealed interface MapEvent : ViewEvent {
    data object Refresh : MapEvent
    data class OnLocationUpdated(val location: Location) : MapEvent
    data class MarkerClick(val id: String) : MapEvent
}

sealed interface MapEffect : ViewEffect {
    data class NavigateToDetails(val pointId: String) : MapEffect
}
