package ru.ermakov.feature_todo.domain.model

import ru.ermakov.core.domain.model.RootError

enum class ToDoError : RootError {
    TO_DO_NOT_FOUND
}