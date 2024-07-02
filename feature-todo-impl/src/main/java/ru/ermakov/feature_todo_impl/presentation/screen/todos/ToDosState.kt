package ru.ermakov.feature_todo_impl.presentation.screen.todos

import ru.ermakov.core.error.RootError
import ru.ermakov.feature_todo_api.domain.model.ToDo

data class ToDosState(
    val toDos: List<ToDo> = emptyList(),
    val numOfDoneToDos: Int = 0,
    val isDoneToDoVisible: Boolean = true,
    val error: RootError? = null
)
