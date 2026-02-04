package org.example.geoblinker.core.utils

object ValidationUtils {
    private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    fun validateEmail(email: String): Boolean = email.matches(EMAIL_REGEX)
    fun validatePassword(password: String): Boolean = password.length >= 8
}
