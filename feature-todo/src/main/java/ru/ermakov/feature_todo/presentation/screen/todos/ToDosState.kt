package ru.ermakov.feature_todo.presentation.screen.todos

import ru.ermakov.core.domain.model.RootError
import ru.ermakov.feature_todo.domain.model.ToDo

data class ToDosState(
    val toDos: List<ToDo> = emptyList(),
    val numOfDoneToDos: Int = 0,
    val isDoneToDoVisible: Boolean = true,
    val error: RootError? = null
)
