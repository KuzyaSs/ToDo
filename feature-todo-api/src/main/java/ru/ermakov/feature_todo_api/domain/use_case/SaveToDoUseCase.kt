package ru.ermakov.feature_todo_api.domain.use_case

import ru.ermakov.core.error.RootError
import ru.ermakov.core.result.Result
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_api.domain.model.ToDoRequest

interface SaveToDoUseCase : suspend (ToDoRequest, ToDo?) -> Result<Unit, RootError>