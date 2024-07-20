package ru.ermakov.feature_todo_impl.presentation.screen.todos

/**
 * Side effects in ToDosScreen.
 */
sealed interface ToDosEffect {
    data class OnNavigateToToDoDestination(val toDoId: String?) : ToDosEffect
    data object OnNavigateToSettingsDestination : ToDosEffect
}