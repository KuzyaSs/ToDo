package ru.ermakov.core.presentation.utils

import android.content.Context
import ru.ermakov.core.R
import ru.ermakov.core.error.CoreError
import ru.ermakov.core.error.RootError

fun RootError.toStringCoreError(context: Context): String {
    return when (this) {
        is CoreError -> when (this) {
            CoreError.OFFLINE_MODE -> context.getString(R.string.offline_mode)
        }

        else -> throw UnsupportedOperationException()
    }
}