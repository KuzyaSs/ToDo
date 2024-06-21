package ru.ermakov.feature_todo.presentation.screen.todos

sealed interface ToDosEvent {
    data class OnToDoItemClick(val toDoId: String) : ToDosEvent
    data object OnDoneToDoVisibilityClick : ToDosEvent
    data object OnAddClick : ToDosEvent
    data object OnNewClick : ToDosEvent
    data class OnDoneChange(val toDoId: String, val isDone: Boolean) : ToDosEvent
}