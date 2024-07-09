package ru.ermakov.feature_todo_impl.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import ru.ermakov.core.error.CoreError
import ru.ermakov.core.error.RootError
import ru.ermakov.core.network_manager.NetworkManager
import ru.ermakov.core.network_manager.NetworkStatus
import ru.ermakov.core.result.Result
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_api.domain.model.ToDoRequest
import ru.ermakov.feature_todo_impl.data.local.data_source.DeviceLocalDataSource
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoLocalDataSource
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoSyncDateLocalDataSource
import ru.ermakov.feature_todo_impl.data.mapper.toRemoteToDo
import ru.ermakov.feature_todo_impl.data.mapper.toToDo
import ru.ermakov.feature_todo_impl.data.remote.data_source.Revision
import ru.ermakov.feature_todo_impl.data.remote.data_source.ToDoRemoteDataSource
import ru.ermakov.feature_todo_impl.domain.repository.ToDoRepository
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val toDoLocalDataSource: ToDoLocalDataSource,
    private val toDoRemoteDataSource: ToDoRemoteDataSource,
    private val toDoSyncDateLocalDataSource: ToDoSyncDateLocalDataSource,
    private val deviceLocalDataSource: DeviceLocalDataSource,
    private val networkManager: NetworkManager
) : ToDoRepository {
    private val _deviceId: String = deviceLocalDataSource.getDeviceId()
    private var isSynced = false

    override fun getToDos(): Flow<Result<List<ToDo>, RootError>> {
        return toDoLocalDataSource.getToDos()
            .combine(networkManager.getNetworkStatus()) { toDosResult, networkStatus ->
                if (networkStatus == NetworkStatus.AVAILABLE) {
                    if (!isSynced) {
                        syncToDos()
                        isSynced = true
                    }
                    Result.Success(data = toDosResult.data)
                } else {
                    isSynced = false
                    Result.Error(data = toDosResult.data, error = CoreError.OFFLINE_MODE)
                }
            }
    }

    override suspend fun getToDoById(toDoId: String): Result<ToDo, RootError> {
        val getToDoByIdResult = toDoRemoteDataSource.getToDoById(toDoId = toDoId)
        return if (getToDoByIdResult is Result.Success) {
            Result.Success(data = getToDoByIdResult.data)
        } else {
            when (val getLocalToDoByIdRequest = toDoLocalDataSource.getToDoById(toDoId = toDoId)) {
                is Result.Success -> Result.Error(
                    data = getLocalToDoByIdRequest.data,
                    error = CoreError.OFFLINE_MODE
                )
                is Result.Error -> Result.Error(error = getLocalToDoByIdRequest.error)
            }
        }
    }


    override suspend fun insertToDo(toDoRequest: ToDoRequest): Result<Unit, RootError> {
        toDoLocalDataSource.upsertToDos(toDos = listOf(toDoRequest.toToDo()))
        val syncToDosResult = syncToDos()
        val insertToDoResult = toDoRemoteDataSource.insertToDo(
            toDo = toDoRequest.toToDo().toRemoteToDo(deviceId = _deviceId),
            revision = if (syncToDosResult is Result.Success) syncToDosResult.data else 0
        )
        return if (syncToDosResult is Result.Success && insertToDoResult is Result.Success) {
            Result.Success(data = Unit)
        } else {
            Result.Error(error = CoreError.OFFLINE_MODE)
        }
    }

    override suspend fun updateToDo(toDo: ToDo): Result<Unit, RootError> {
        toDoLocalDataSource.upsertToDos(toDos = listOf(toDo))
        val syncToDosResult = syncToDos()
        val updateToDosResult = toDoRemoteDataSource.updateToDo(
            toDo = toDo.toRemoteToDo(deviceId = _deviceId),
            revision = if (syncToDosResult is Result.Success) syncToDosResult.data else 0
        )
        return if (syncToDosResult is Result.Success && updateToDosResult is Result.Success) {
            Result.Success(data = Unit)
        } else {
            Result.Error(error = CoreError.OFFLINE_MODE)
        }
    }

    override suspend fun deleteToDoById(toDoId: String): Result<Unit, RootError> {
        toDoLocalDataSource.deleteToDoById(toDoId = toDoId)
        val syncToDosResult = syncToDos(deletedToDoId = toDoId)
        val deleteToDosResult = toDoRemoteDataSource.deleteToDoById(
            toDoId = toDoId,
            revision = if (syncToDosResult is Result.Success) syncToDosResult.data else 0
        )
        return if (syncToDosResult is Result.Success && deleteToDosResult is Result.Success) {
            Result.Success(data = Unit)
        } else {
            Result.Error(error = CoreError.OFFLINE_MODE)
        }
    }

    override suspend fun syncToDos(deletedToDoId: String): Result<Revision, RootError> {
        return when (val remoteToDosResult = toDoRemoteDataSource.getToDos()) {
            is Result.Success -> {
                val syncedToDos = mutableListOf<ToDo>()
                val revision = remoteToDosResult.data.second
                val lastSyncDate = toDoSyncDateLocalDataSource.getToDoSyncDate()
                val cachedToDos = toDoLocalDataSource.getCurrentToDos().data
                val cachedToDoIds = cachedToDos.map { cacheToDo -> cacheToDo.id } - deletedToDoId
                val remoteToDos = remoteToDosResult.data.first
                val remoteToDoIds = remoteToDos.map { remoteToDo -> remoteToDo.id } - deletedToDoId
                syncedToDos.addAll(
                    getSyncedOnlyCachedToDos(
                        cachedToDos = cachedToDos,
                        onlyCachedToDoIds = cachedToDoIds.subtract(remoteToDoIds.toSet()),
                        lastSyncDate = lastSyncDate
                    )
                )
                syncedToDos.addAll(
                    getSyncedOnlyRemoteToDos(
                        remoteToDos = remoteToDos,
                        onlyRemoteToDoIds = remoteToDoIds.subtract(cachedToDoIds.toSet()),
                        lastSyncDate = lastSyncDate
                    )
                )
                syncedToDos.addAll(
                    getSyncedSharedToDos(
                        cachedToDos = cachedToDos,
                        remoteToDos = remoteToDos,
                        sharedToDoIds = cachedToDoIds.intersect(remoteToDoIds.toSet())
                    )
                )
                toDoLocalDataSource.upsertToDos(toDos = syncedToDos)
                toDoRemoteDataSource.updateToDos(
                    toDos = syncedToDos.map { toDo -> toDo.toRemoteToDo(deviceId = _deviceId) },
                    revision = revision
                )
                toDoSyncDateLocalDataSource.setToDoSyncDateToCurrentDate()
                Result.Success(data = revision)
            }
            is Result.Error -> Result.Error(error = CoreError.OFFLINE_MODE)
        }
    }

    private fun getSyncedOnlyCachedToDos(
        cachedToDos: List<ToDo>,
        onlyCachedToDoIds: Set<String>,
        lastSyncDate: LocalDateTime
    ): List<ToDo> {
        return cachedToDos.filter { cachedToDo ->
            (cachedToDo.modificationDate?.compareTo(lastSyncDate) ?: 0) > 0
                    && onlyCachedToDoIds.contains(cachedToDo.id)
        }
    }

    private fun getSyncedOnlyRemoteToDos(
        remoteToDos: List<ToDo>,
        onlyRemoteToDoIds: Set<String>,
        lastSyncDate: LocalDateTime
    ): List<ToDo> {
        return remoteToDos.filter { remoteToDo ->
            (remoteToDo.modificationDate?.compareTo(lastSyncDate) ?: 0) > 0
                    && onlyRemoteToDoIds.contains(remoteToDo.id)
        }
    }

    private fun getSyncedSharedToDos(
        cachedToDos: List<ToDo>,
        remoteToDos: List<ToDo>,
        sharedToDoIds: Set<String>
    ): List<ToDo> {
        val sharedCachedToDos = cachedToDos.filter { cachedToDo ->
            sharedToDoIds.contains(cachedToDo.id)
        }
        val sharedRemoteToDos = remoteToDos.filter { remoteToDo ->
            sharedToDoIds.contains(remoteToDo.id)
        }
        return sharedCachedToDos.map { cachedToDo ->
            val remoteToDo =
                sharedRemoteToDos.first { remoteToDo -> remoteToDo.id == cachedToDo.id }
            val remoteEpochSeconds = remoteToDo.modificationDate
                ?.toInstant(TimeZone.currentSystemDefault())?.epochSeconds ?: 0
            val cacheEpochSeconds = cachedToDo.modificationDate
                ?.toInstant(TimeZone.currentSystemDefault())?.epochSeconds ?: 0
            if (remoteEpochSeconds >= cacheEpochSeconds) {
                remoteToDo
            } else {
                cachedToDo
            }
        }
    }
}