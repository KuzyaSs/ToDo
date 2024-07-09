package ru.ermakov.feature_todo_impl.presentation.screen.todos

import ru.ermakov.feature_todo_api.domain.model.ToDo

/**
 * UI state of ToDosScreen.
 */
data class ToDosState(
    val toDos: List<ToDo> = emptyList(),
    val numOfDoneToDos: Int = 0,
    val isDoneToDoVisible: Boolean = true,
    val isOfflineMode: Boolean = false,
    val isLoading: Boolean = true,
)
