package ru.ermakov.feature_todo_api.domain.use_case

import ru.ermakov.core.result.Result
import ru.ermakov.core.error.RootError
import ru.ermakov.feature_todo_api.domain.model.ToDo

interface ChangeDoneByToDoIdUseCase : suspend (ToDo, Boolean) -> Result<Unit, RootError>