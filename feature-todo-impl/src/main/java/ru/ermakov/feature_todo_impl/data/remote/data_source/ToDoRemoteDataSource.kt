package ru.ermakov.feature_todo_impl.data.remote.data_source

import ru.ermakov.core.error.RootError
import ru.ermakov.core.result.Result
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_impl.data.remote.model.RemoteToDo

interface ToDoRemoteDataSource {
    suspend fun getToDos(): Result<Pair<List<ToDo>, Revision>, RootError>
    suspend fun getToDoById(toDoId: String): Result<ToDo, RootError>
    suspend fun insertToDo(toDo: RemoteToDo, revision: Revision): Result<Unit, RootError>
    suspend fun updateToDos(toDos: List<RemoteToDo>, revision: Revision): Result<Unit, RootError>
    suspend fun updateToDo(toDo: RemoteToDo, revision: Revision): Result<Unit, RootError>
    suspend fun deleteToDoById(toDoId: String, revision: Revision): Result<Unit, RootError>
}