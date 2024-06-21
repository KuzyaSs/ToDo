package ru.ermakov.feature_todo.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class ToDo(
    val id: String,
    val content: String,
    val priority: Priority,
    val isDone: Boolean,
    val creationDate: LocalDateTime,
    val modificationDate: LocalDateTime?,
    val deadline: LocalDate?
)