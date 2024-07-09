package ru.ermakov.network.data.utils

import ru.ermakov.core.error.RootError
import ru.ermakov.network.domain.NetworkError

fun Int.localizeRemoteErrorByCode(): RootError {
    return when (this) {
        400 -> NetworkError.UNSYNCHRONIZED_DATA
        401 -> NetworkError.UNAUTHORIZED
        404 -> NetworkError.NOT_FOUND
        else -> NetworkError.SERVER_ERROR
    }
}