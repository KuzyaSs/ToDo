package ru.ermakov.feature_todo_impl.data.remote.utils

import ru.ermakov.core.error.RootError
import ru.ermakov.feature_todo_api.domain.model.ToDoError
import ru.ermakov.network.data.utils.localizeRemoteErrorByCode
import ru.ermakov.network.domain.NetworkError

internal fun Int.localizeRemoteToDoErrorByCode(): RootError {
    return when (this) {
        404 -> ToDoError.TO_DO_NOT_FOUND
        else -> this.localizeRemoteErrorByCode()
    }
}