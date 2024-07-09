package ru.ermakov.feature_todo_impl.domain.use_case

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.ermakov.core.result.Result
import ru.ermakov.core.error.RootError
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_api.domain.use_case.ChangeDoneByToDoIdUseCase
import ru.ermakov.feature_todo_impl.domain.repository.ToDoRepository

class ChangeDoneByToDoIdUseCaseImpl(
    private val toDoRepository: ToDoRepository
) : ChangeDoneByToDoIdUseCase {
    override suspend fun invoke(toDo: ToDo, isDone: Boolean): Result<Unit, RootError> {
        return toDoRepository.updateToDo(
            toDo = toDo.copy(
                isDone = isDone,
                modificationDate = Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault())
            )
        )
    }
}