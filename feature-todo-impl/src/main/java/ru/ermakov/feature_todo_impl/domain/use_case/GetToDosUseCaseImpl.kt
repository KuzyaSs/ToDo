package ru.ermakov.feature_todo_impl.domain.use_case

import kotlinx.coroutines.flow.Flow
import ru.ermakov.core.result.Result
import ru.ermakov.core.error.RootError
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_api.domain.use_case.GetToDosUseCase
import ru.ermakov.feature_todo_impl.domain.repository.ToDoRepository

class GetToDosUseCaseImpl(private val toDoRepository: ToDoRepository) : GetToDosUseCase {
    override suspend fun invoke(): Flow<Result<List<ToDo>, RootError>> {
        return toDoRepository.getToDos()
    }
}