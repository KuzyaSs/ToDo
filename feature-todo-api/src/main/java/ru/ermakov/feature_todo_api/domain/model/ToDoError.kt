package ru.ermakov.feature_todo_api.domain.model

import ru.ermakov.core.error.RootError

enum class ToDoError : RootError {
    TO_DO_NOT_FOUND
}