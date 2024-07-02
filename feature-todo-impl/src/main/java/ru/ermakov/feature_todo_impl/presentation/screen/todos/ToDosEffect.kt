package ru.ermakov.feature_todo_impl.presentation.screen.todos

sealed interface ToDosEffect {
    data class OnNavigateToToDoDestination(val toDoId: String?) : ToDosEffect
}