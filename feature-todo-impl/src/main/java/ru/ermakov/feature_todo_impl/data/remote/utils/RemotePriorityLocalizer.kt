package ru.ermakov.feature_todo_impl.data.remote.utils

import ru.ermakov.feature_todo_api.domain.model.Priority

fun String.toPriority(): Priority {
    return when (this) {
        "low" -> Priority.LOW
        "important" -> Priority.URGENT
        else -> Priority.NORMAL
    }
}

fun Priority.toStringPriority(): String {
    return when (this) {
        Priority.LOW -> "low"
        Priority.NORMAL -> "basic"
        Priority.URGENT -> "important"
    }
}