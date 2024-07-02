package ru.ermakov.feature_todo_impl.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import ru.ermakov.core.error.CoreError
import ru.ermakov.core.network_manager.NetworkStatus
import ru.ermakov.core.result.Result
import ru.ermakov.core.error.RootError
import ru.ermakov.core.network_manager.NetworkManager
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_api.domain.model.ToDoRequest
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoLocalDataSource
import ru.ermakov.feature_todo_impl.data.mapper.toToDo
import ru.ermakov.feature_todo_impl.domain.repository.ToDoRepository
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val toDoLocalDataSource: ToDoLocalDataSource,
    private val networkManager: NetworkManager
) : ToDoRepository {

    override fun getToDos(): Flow<Result<List<ToDo>, RootError>> {
        return toDoLocalDataSource.getToDos()
            .combine(networkManager.getNetworkStatus()) { toDosResult, networkStatus ->
                when (networkStatus) {
                    NetworkStatus.AVAILABLE -> {
                        syncToDos()
                        Result.Success(data = if (toDosResult is Result.Success) toDosResult.data else emptyList())
                    }

                    NetworkStatus.LOST -> {
                        Result.Error(
                            data = if (toDosResult is Result.Success) toDosResult.data else null,
                            error = CoreError.OFFLINE_MODE
                        )
                    }
                }
            }
    }

    override suspend fun getToDoById(toDoId: String): Result<ToDo, RootError> {
        return toDoLocalDataSource.getToDoById(toDoId = toDoId)
    }

    override suspend fun insertToDo(toDoRequest: ToDoRequest): Result<Unit, RootError> {
        syncToDos()
        return toDoLocalDataSource.upsertToDos(toDos = listOf(toDoRequest.toToDo()))
    }

    override suspend fun updateToDo(toDo: ToDo): Result<Unit, RootError> {
        syncToDos()
        return toDoLocalDataSource.upsertToDos(toDos = listOf(toDo))
    }

    override suspend fun deleteToDoById(toDoId: String): Result<Unit, RootError> {
        syncToDos()
        return toDoLocalDataSource.deleteToDoById(toDoId = toDoId)
    }

    private suspend fun syncToDos() {

    }
}