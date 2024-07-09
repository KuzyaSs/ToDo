package ru.ermakov.feature_todo_impl.presentation.screen.todo

import ru.ermakov.core.error.RootError

/**
 * Side effects in ToDoScreen.
 */
sealed interface ToDoEffect {
    data object OnNavigateBack : ToDoEffect
    data class ShowSnackBarErrorMessage(val error: RootError) : ToDoEffect
}