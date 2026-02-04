package org.example.geoblinker.presentation.features.details

import org.example.geoblinker.core.base.ViewEvent
import org.example.geoblinker.core.base.ViewSideEffect
import org.example.geoblinker.core.base.ViewState

class PlaceDetailsContract {
    data class State(
        val isLoading: Boolean = false,
        val placeName: String = "",
        val address: String = "",
        val description: String = "",
        val rating: Float = 0f
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnBackClicked : Event()
        object OnFavoriteClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        object NavigateBack : Effect()
    }
}
