package ru.ermakov.core.result

import ru.ermakov.core.error.RootError

sealed interface Result<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Result<D, E>
    data class Error<out D, out E : RootError>(val data: D? = null, val error: E) : Result<D, E>
}