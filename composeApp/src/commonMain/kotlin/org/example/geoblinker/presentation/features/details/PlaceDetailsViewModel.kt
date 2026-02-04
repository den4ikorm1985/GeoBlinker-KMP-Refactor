package org.example.geoblinker.presentation.features.details

import kotlinx.coroutines.launch
import org.example.geoblinker.core.base.BaseViewModel
import org.example.geoblinker.domain.repository.PlacesRepository
import org.example.geoblinker.presentation.features.details.PlaceDetailsContract.*

class PlaceDetailsViewModel(
    private val repository: PlacesRepository,
    private val placeId: String
) : BaseViewModel<State, Event, Effect>(State()) {

    init {
        loadPlaceDetails()
    }

    override fun onEvent(event: Event) {
        when (event) {
            Event.OnBackClicked -> emitEffect(Effect.NavigateBack)
            Event.OnFavoriteClicked -> toggleFavorite()
        }
    }

    private fun loadPlaceDetails() {
        updateState { it.copy(isLoading = true) }
        coroutineScope.launch {
            runCatching {
                repository.getPlaceDetails(placeId)
            }.onSuccess { place ->
                updateState { it.copy(
                    isLoading = false,
                    placeName = place.name,
                    address = place.address,
                    description = place.description,
                    rating = place.rating.toFloat()
                ) }
            }.onFailure {
                updateState { it.copy(isLoading = false) }
                // Тут добавим обработку ошибки через Effect
            }
        }
    }

    private fun toggleFavorite() {
        // Логика избранного
    }
}
