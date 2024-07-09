package ru.ermakov.network.domain

import ru.ermakov.core.error.RootError

enum class NetworkError : RootError {
    UNAUTHORIZED,
    UNSYNCHRONIZED_DATA,
    NOT_FOUND,
    SERVER_ERROR,
}