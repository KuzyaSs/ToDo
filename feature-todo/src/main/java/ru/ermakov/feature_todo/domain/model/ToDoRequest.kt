package ru.ermakov.feature_todo.domain.model

import kotlinx.datetime.LocalDate

data class ToDoRequest(
    val id: String?,
    val content: String,
    val priority: Priority,
    val deadline: LocalDate?
)