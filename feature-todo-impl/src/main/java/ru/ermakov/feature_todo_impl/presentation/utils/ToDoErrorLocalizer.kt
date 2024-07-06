package ru.ermakov.feature_todo_impl.presentation.utils

import android.content.Context
import ru.ermakov.core.error.CoreError
import ru.ermakov.core.error.RootError
import ru.ermakov.core.presentation.utils.toStringCoreError
import ru.ermakov.feature_todo_api.domain.model.ToDoError
import ru.ermakov.feature_todo_impl.R

fun RootError.toStringToDoError(context: Context): String {
    return when (this) {
        is CoreError -> this.toStringCoreError(context = context)
        is ToDoError -> when (this) {
            ToDoError.TO_DO_NOT_FOUND -> context.getString(R.string.to_do_not_found)
            ToDoError.OVERDUE_DEADLINE -> context.getString(R.string.overdue_deadline)
        }

        else -> throw UnsupportedOperationException()
    }
}