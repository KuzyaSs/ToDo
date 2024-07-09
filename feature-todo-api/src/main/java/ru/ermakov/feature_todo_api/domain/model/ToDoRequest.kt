package ru.ermakov.feature_todo_api.domain.model

import kotlinx.datetime.LocalDate

data class ToDoRequest(
    val content: String,
    val priority: Priority,
    val deadline: LocalDate?
)