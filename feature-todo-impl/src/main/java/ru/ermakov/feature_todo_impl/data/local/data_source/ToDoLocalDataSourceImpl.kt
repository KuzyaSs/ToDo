package ru.ermakov.feature_todo_impl.data.local.data_source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ermakov.core.result.Result
import ru.ermakov.core.error.RootError
import ru.ermakov.feature_todo_api.data.local.dao.ToDoDao
import ru.ermakov.feature_todo_api.data.local.model.LocalToDo
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_api.domain.model.ToDoError
import ru.ermakov.feature_todo_impl.data.mapper.toLocalToDo
import ru.ermakov.feature_todo_impl.data.mapper.toToDo
import javax.inject.Inject

class ToDoLocalDataSourceImpl @Inject constructor(
    private val toDoDao: ToDoDao
) : ToDoLocalDataSource {
    override fun getToDos(): Flow<Result.Success<List<ToDo>, RootError>> {
        return toDoDao.getToDos().map { localToDos ->
            Result.Success(data = localToDos.map { localToDo -> localToDo.toToDo() })
        }
    }

    override suspend fun getCurrentToDos(): Result.Success<List<ToDo>, RootError> {
        return Result.Success(
            data = toDoDao.getCurrentToDos().map { localToDo -> localToDo.toToDo() }
        )
    }

    override suspend fun getToDoById(toDoId: String): Result<ToDo, RootError> {
        val localToDo = toDoDao.getToDoById(toDoId = toDoId)
        return if (localToDo != null) {
            Result.Success(data = localToDo.toToDo())
        } else {
            Result.Error(error = ToDoError.TO_DO_NOT_FOUND)
        }
    }

    override suspend fun upsertToDos(toDos: List<ToDo>): Result<Unit, RootError> {
        toDoDao.upsertToDos(localToDos = toDos.map { toDo -> toDo.toLocalToDo() })
        return Result.Success(data = Unit)
    }

    override suspend fun deleteToDoById(toDoId: String): Result<Unit, RootError> {
        toDoDao.deleteToDoById(toDoId = toDoId)
        return Result.Success(data = Unit)
    }
}