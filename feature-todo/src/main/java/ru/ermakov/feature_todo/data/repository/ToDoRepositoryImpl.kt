package ru.ermakov.feature_todo.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.ermakov.core.domain.model.Result
import ru.ermakov.core.domain.model.RootError
import ru.ermakov.feature_todo.domain.model.Priority
import ru.ermakov.feature_todo.domain.model.ToDo
import ru.ermakov.feature_todo.domain.model.ToDoError
import ru.ermakov.feature_todo.domain.model.ToDoRequest
import ru.ermakov.feature_todo.domain.repository.ToDoRepository
import java.util.UUID
import javax.inject.Inject

/**
Класс [ToDoRepositoryImpl] до добавления локальной базы данных и HTTP-клиента содержит поле
[ToDoRepositoryImpl.toDoS] для хранения созданных пользователем дел. На следующих д/з с
соответствующими темами реализация всех методов будет изменена. На данный момент разработаны
методы-заглушки, так что можно сюда не смотреть)
 */
class ToDoRepositoryImpl @Inject constructor() : ToDoRepository {
    private val toDoS = MutableStateFlow(buildList {
        add(
            ToDo(
                "1",
                "Some content",
                Priority.URGENT,
                true,
                Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                null,
                Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date
            )
        )
        add(
            ToDo(
                "2",
                "Some content",
                Priority.URGENT,
                false,
                Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                null,
                null
            )
        )
        add(
            ToDo(
                "3",
                "Some content Some content Some content Some content Some content Some content Some content Some content Some content Some content Some content Some content Some content Some content ",
                Priority.NORMAL,
                true,
                Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                null,
                null
            )
        )
        add(
            ToDo(
                "4",
                "Some content Some content Some content ",
                Priority.LOW,
                true,
                Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                null,
                null
            )
        )
    })

    override fun getToDos(): Flow<Result<List<ToDo>, RootError>> {
        return toDoS.map { toDos ->
            Result.Success(data = toDos.sortedByDescending { toDo -> toDo.modificationDate })
        }
    }

    override suspend fun getToDoById(toDoId: String): Result<ToDo, RootError> {
        val toDo = toDoS.value.firstOrNull { toDo -> toDo.id == toDoId }
        return if (toDo != null) {
            Result.Success(data = toDo)
        } else {
            Result.Error(error = ToDoError.TO_DO_NOT_FOUND)
        }
    }

    override suspend fun insertTodo(toDoRequest: ToDoRequest): Result<Unit, RootError> {
        toDoS.update { toDos ->
            toDos + ToDo(
                id = UUID.randomUUID().toString(),
                content = toDoRequest.content,
                priority = toDoRequest.priority,
                isDone = false,
                creationDate = Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                modificationDate = Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                deadline = toDoRequest.deadline
            )
        }
        return Result.Success(data = Unit)
    }

    override suspend fun updateTodo(toDoRequest: ToDoRequest): Result<Unit, RootError> {
        toDoS.update { toDos ->
            toDos.map { toDo ->
                if (toDo.id == toDoRequest.id) {
                    toDo.copy(
                        content = toDoRequest.content,
                        priority = toDoRequest.priority,
                        modificationDate = Clock.System.now()
                            .toLocalDateTime(TimeZone.currentSystemDefault()),
                        deadline = toDoRequest.deadline
                    )
                } else {
                    toDo
                }
            }
        }
        return Result.Success(data = Unit)
    }

    override suspend fun changeDoneByToDoId(
        toDoId: String,
        isDone: Boolean
    ): Result<Unit, RootError> {
        toDoS.update { toDos ->
            toDos.map { toDo ->
                if (toDo.id == toDoId) {
                    toDo.copy(isDone = isDone)
                } else {
                    toDo
                }
            }
        }
        return Result.Success(data = Unit)
    }

    override suspend fun deleteToDoById(toDoId: String): Result<Unit, RootError> {
        toDoS.update { toDos ->
            toDos.filter { toDo -> toDo.id != toDoId }
        }
        return Result.Success(data = Unit)
    }
}