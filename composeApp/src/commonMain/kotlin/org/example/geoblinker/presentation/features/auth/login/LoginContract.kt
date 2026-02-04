package org.example.geoblinker.presentation.features.auth.login
import org.example.geoblinker.core.base.*

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewState

sealed interface LoginEvent : ViewEvent {
    data class EmailChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    data object LoginClick : LoginEvent
    data object RegisterClick : LoginEvent
}

sealed interface LoginEffect : ViewEffect {
    data object NavigateToMap : LoginEffect
    data object NavigateToRegister : LoginEffect
    data class ShowError(val message: String) : LoginEffect
}
