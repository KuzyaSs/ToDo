package ru.ermakov.feature_todo_impl.domain.use_case

import ru.ermakov.core.error.RootError
import ru.ermakov.core.result.Result
import ru.ermakov.feature_todo_api.domain.model.ToDoError
import ru.ermakov.feature_todo_api.domain.use_case.DeleteToDoByIdUseCase
import ru.ermakov.feature_todo_impl.domain.repository.ToDoRepository

class DeleteToDoByIdUseCaseImpl(
    private val toDoRepository: ToDoRepository
) : DeleteToDoByIdUseCase {
    override suspend fun invoke(toDoId: String?): Result<Unit, RootError> {
        if (toDoId == null) {
            return Result.Error(error = ToDoError.TO_DO_NOT_FOUND)
        }
        return toDoRepository.deleteToDoById(toDoId = toDoId)
    }
}