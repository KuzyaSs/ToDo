package ru.ermakov.feature_todo_impl.presentation.screen.todos

import ru.ermakov.feature_todo_api.domain.model.ToDo

/**
 * Events in ToDosScreen.
 */
sealed interface ToDosEvent {
    data class OnToDoItemClick(val toDoId: String) : ToDosEvent
    data object OnDoneToDoVisibilityClick : ToDosEvent
    data object OnAddClick : ToDosEvent
    data object OnNewClick : ToDosEvent
    data class OnDoneChange(val toDo: ToDo, val isDone: Boolean) : ToDosEvent
}