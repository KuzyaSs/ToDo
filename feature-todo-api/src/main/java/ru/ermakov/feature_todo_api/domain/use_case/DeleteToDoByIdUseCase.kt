package ru.ermakov.feature_todo_api.domain.use_case

import ru.ermakov.core.error.RootError
import ru.ermakov.core.result.Result

interface DeleteToDoByIdUseCase : suspend (String?) -> Result<Unit, RootError>