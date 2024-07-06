package ru.ermakov.feature_todo_impl.presentation.screen.todos

import ru.ermakov.core.error.RootError

/**
 * Side effects in ToDosScreen.
 */
sealed interface ToDosEffect {
    data class OnNavigateToToDoDestination(val toDoId: String?) : ToDosEffect
    data class ShowSnackBarErrorMessage(val error: RootError) : ToDosEffect
}