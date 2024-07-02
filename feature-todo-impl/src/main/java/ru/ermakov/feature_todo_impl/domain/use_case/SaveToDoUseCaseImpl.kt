package ru.ermakov.feature_todo_impl.domain.use_case

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.ermakov.core.error.RootError
import ru.ermakov.core.result.Result
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_api.domain.model.ToDoRequest
import ru.ermakov.feature_todo_api.domain.use_case.SaveToDoUseCase
import ru.ermakov.feature_todo_impl.domain.repository.ToDoRepository

class SaveToDoUseCaseImpl(private val toDoRepository: ToDoRepository) : SaveToDoUseCase {
    override suspend fun invoke(
        todoRequest: ToDoRequest,
        savedToDo: ToDo?
    ): Result<Unit, RootError> {
        return if (savedToDo != null) {
            toDoRepository.updateToDo(
                toDo = ToDo(
                    id = savedToDo.id,
                    content = todoRequest.content,
                    priority = todoRequest.priority,
                    isDone = savedToDo.isDone,
                    creationDate = savedToDo.creationDate,
                    modificationDate = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                    deadline = todoRequest.deadline
                )
            )
        } else {
            toDoRepository.insertToDo(toDoRequest = todoRequest)
        }
    }
}