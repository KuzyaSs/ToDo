package ru.ermakov.feature_todo.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.ermakov.core.domain.model.Result
import ru.ermakov.core.domain.model.RootError
import ru.ermakov.feature_todo.domain.model.ToDo
import ru.ermakov.feature_todo.domain.model.ToDoRequest

interface ToDoRepository {
    fun getToDos(): Flow<Result<List<ToDo>, RootError>>
    suspend fun getToDoById(toDoId: String): Result<ToDo, RootError>
    suspend fun insertTodo(toDoRequest: ToDoRequest): Result<Unit, RootError>
    suspend fun updateTodo(toDoRequest: ToDoRequest): Result<Unit, RootError>
    suspend fun changeDoneByToDoId(toDoId: String, isDone: Boolean): Result<Unit, RootError>
    suspend fun deleteToDoById(toDoId: String): Result<Unit, RootError>
}