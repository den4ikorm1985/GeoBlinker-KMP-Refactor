package org.example.geoblinker.util
import kotlinx.atomicfu.*
class RecentSearchManagerImpl {
    private val _searches = atomic(listOf<String>())
    fun addSearch(query: String) {
        val trimmed = query.trim()
        if (trimmed.isBlank()) return
        _searches.update { current ->
            (listOf(trimmed) + current.filter { it != trimmed }).take(10)
        }
    }
    fun getRecentSearches(): List<String> = _searches.value
}
