package ru.ermakov.feature_todo_impl.presentation.screen.todo

sealed interface ToDoEffect {
    data object OnNavigateBack : ToDoEffect
}