package org.example.geoblinker.presentation.features.auth.login
import org.example.geoblinker.core.base.BaseViewModel
import org.example.geoblinker.core.utils.ValidationUtils
import kotlinx.coroutines.*

class LoginViewModel(
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>(LoginState()) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> updateState { it.copy(email = event.value) }
            is LoginEvent.PasswordChanged -> updateState { it.copy(password = event.value) }
            LoginEvent.RegisterClick -> emitEffect(LoginEffect.NavigateToRegister)
            LoginEvent.LoginClick -> performLogin()
        }
    }

    private fun performLogin() {
        val email = currentState.email
        val password = currentState.password

        if (!ValidationUtils.validateEmail(email)) {
            updateState { it.copy(error = "Некорректный email") }
            return
        }
        if (!ValidationUtils.validatePassword(password)) {
            updateState { it.copy(error = "Пароль слишком короткий (мин. 8 симв.)") }
            return
        }

        updateState { it.copy(isLoading = true, error = null) }

        coroutineScope.launch {
            try {
                withContext(ioDispatcher) {
                    delay(1000) // Имитация сети
                }
                updateState { it.copy(isLoading = false) }
                emitEffect(LoginEffect.NavigateToMap)
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                updateState { it.copy(isLoading = false, error = e.message ?: "Ошибка входа") }
            }
        }
    }
}
