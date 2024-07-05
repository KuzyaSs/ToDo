package ru.ermakov.feature_todo_impl.data.local.data_source

import kotlinx.coroutines.flow.Flow
import ru.ermakov.core.result.Result
import ru.ermakov.core.error.RootError
import ru.ermakov.feature_todo_api.domain.model.ToDo

interface ToDoLocalDataSource {
    fun getToDos(): Flow<Result.Success<List<ToDo>, RootError>>
    suspend fun getCurrentToDos(): Result.Success<List<ToDo>, RootError>
    suspend fun getToDoById(toDoId: String): Result<ToDo, RootError>
    suspend fun upsertToDos(toDos: List<ToDo>): Result<Unit, RootError>
    suspend fun deleteToDoById(toDoId: String): Result<Unit, RootError>
}