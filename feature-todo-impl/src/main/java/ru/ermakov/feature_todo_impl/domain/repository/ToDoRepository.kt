package ru.ermakov.feature_todo_impl.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.ermakov.core.error.RootError
import ru.ermakov.core.result.Result
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_api.domain.model.ToDoRequest

/**
 * Abstracts the way to get to-dos.
 */
interface ToDoRepository {
    fun getToDos(): Flow<Result<List<ToDo>, RootError>>
    suspend fun getToDoById(toDoId: String): Result<ToDo, RootError>
    suspend fun insertToDo(toDoRequest: ToDoRequest): Result<Unit, RootError>
    suspend fun updateToDo(toDo: ToDo): Result<Unit, RootError>
    suspend fun deleteToDoById(toDoId: String): Result<Unit, RootError>
    suspend fun syncToDos(): Result<Long, RootError>
}