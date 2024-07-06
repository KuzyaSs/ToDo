package ru.ermakov.feature_todo_api.domain.use_case

import ru.ermakov.core.error.RootError
import ru.ermakov.core.result.Result
import ru.ermakov.feature_todo_api.domain.model.ToDo

interface GetToDoByIdUseCase : suspend (String) -> Result<ToDo, RootError>