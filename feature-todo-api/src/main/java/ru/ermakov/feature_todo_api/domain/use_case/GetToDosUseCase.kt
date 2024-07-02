package ru.ermakov.feature_todo_api.domain.use_case

import kotlinx.coroutines.flow.Flow
import ru.ermakov.core.result.Result
import ru.ermakov.core.error.RootError
import ru.ermakov.feature_todo_api.domain.model.ToDo

interface GetToDosUseCase : suspend () -> Flow<Result<List<ToDo>, RootError>>