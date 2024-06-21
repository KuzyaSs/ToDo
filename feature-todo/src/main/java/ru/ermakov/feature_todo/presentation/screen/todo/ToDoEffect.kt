package ru.ermakov.feature_todo.presentation.screen.todo

sealed interface ToDoEffect {
    data object OnNavigateBack : ToDoEffect
}