package ru.ermakov.feature_todo_impl.data.remote.data_source

import ru.ermakov.core.error.CoreError
import ru.ermakov.core.error.RootError
import ru.ermakov.core.result.Result
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_impl.data.mapper.toToDo
import ru.ermakov.feature_todo_impl.data.remote.api.ToDoApi
import ru.ermakov.feature_todo_impl.data.remote.model.RemoteToDo
import ru.ermakov.feature_todo_impl.data.remote.model.RemoteToDos
import ru.ermakov.feature_todo_impl.data.remote.utils.localizeRemoteToDoErrorByCode
import javax.inject.Inject

internal typealias Revision = Long

class ToDoRemoteDataSourceImpl @Inject constructor(
    private val toDoApi: ToDoApi
) : ToDoRemoteDataSource {
    override suspend fun getToDos(): Result<Pair<List<ToDo>, Revision>, RootError> {
        try {
            val remoteToDosResponse = toDoApi.getToDos()
            if (remoteToDosResponse.isSuccessful) {
                remoteToDosResponse.body()?.let {
                    return Result.Success(
                        data = Pair(
                            it.remoteToDos.map { remoteToDo -> remoteToDo.toToDo() },
                            it.revision
                        )
                    )
                }
            }
            return Result.Error(error = remoteToDosResponse.code().localizeRemoteToDoErrorByCode())
        } catch (exception: Exception) {
            return Result.Error(error = CoreError.OFFLINE_MODE)
        }
    }

    override suspend fun getToDoById(toDoId: String): Result<ToDo, RootError> {
        try {
            val remoteToDoResponse = toDoApi.getToDoById(toDoId = toDoId)
            if (remoteToDoResponse.isSuccessful) {
                remoteToDoResponse.body()?.let {
                    return Result.Success(data = it.remoteToDo.toToDo())
                }
            }
            return Result.Error(error = remoteToDoResponse.code().localizeRemoteToDoErrorByCode())
        } catch (exception: Exception) {
            return Result.Error(error = CoreError.OFFLINE_MODE)
        }
    }

    override suspend fun insertToDo(toDo: RemoteToDo, revision: Revision): Result<Unit, RootError> {
        try {
            val response = toDoApi.insertToDo(toDo = toDo, revision = revision)
            if (response.isSuccessful) {
                return Result.Success(data = Unit)
            }
            return Result.Error(error = response.code().localizeRemoteToDoErrorByCode())
        } catch (exception: Exception) {
            return Result.Error(error = CoreError.OFFLINE_MODE)
        }
    }

    override suspend fun updateToDos(
        toDos: List<RemoteToDo>,
        revision: Revision
    ): Result<Unit, RootError> {
        try {
            val response =
                toDoApi.updateToDos(toDos = RemoteToDos(toDos = toDos), revision = revision)
            if (response.isSuccessful) {
                return Result.Success(data = Unit)
            }
            return Result.Error(error = response.code().localizeRemoteToDoErrorByCode())
        } catch (exception: Exception) {
            return Result.Error(error = CoreError.OFFLINE_MODE)
        }
    }

    override suspend fun updateToDo(toDo: RemoteToDo, revision: Revision): Result<Unit, RootError> {
        try {
            val response = toDoApi.updateToDo(toDo = toDo, toDoId = toDo.id, revision = revision)
            if (response.isSuccessful) {
                return Result.Success(data = Unit)
            }
            return Result.Error(error = response.code().localizeRemoteToDoErrorByCode())
        } catch (exception: Exception) {
            return Result.Error(error = CoreError.OFFLINE_MODE)
        }
    }

    override suspend fun deleteToDoById(
        toDoId: String,
        revision: Revision
    ): Result<Unit, RootError> {
        try {
            val response = toDoApi.deleteToDoById(toDoId = toDoId, revision = revision)
            if (response.isSuccessful) {
                return Result.Success(data = Unit)
            }
            return Result.Error(error = response.code().localizeRemoteToDoErrorByCode())
        } catch (exception: Exception) {
            return Result.Error(error = CoreError.OFFLINE_MODE)
        }
    }
}