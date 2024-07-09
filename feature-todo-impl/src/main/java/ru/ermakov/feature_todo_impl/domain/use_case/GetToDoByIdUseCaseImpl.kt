package ru.ermakov.feature_todo_impl.domain.use_case

import ru.ermakov.core.error.RootError
import ru.ermakov.core.result.Result
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_api.domain.model.ToDoError
import ru.ermakov.feature_todo_api.domain.use_case.GetToDoByIdUseCase
import ru.ermakov.feature_todo_impl.domain.repository.ToDoRepository

class GetToDoByIdUseCaseImpl(private val toDoRepository: ToDoRepository) : GetToDoByIdUseCase {
    override suspend fun invoke(toDoId: String): Result<ToDo, RootError> {
        return toDoRepository.getToDoById(toDoId = toDoId)
    }
}