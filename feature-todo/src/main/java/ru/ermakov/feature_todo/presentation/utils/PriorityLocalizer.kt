package ru.ermakov.feature_todo.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.ermakov.feature_todo.R
import ru.ermakov.feature_todo.domain.model.Priority

@Composable
fun Priority.toStringPriority(): String {
    return when (this) {
        Priority.LOW -> stringResource(id = R.string.low_priority_selection)
        Priority.NORMAL -> stringResource(id = R.string.normal_priority_selection)
        Priority.URGENT -> stringResource(id = R.string.urgent_priority_selection)
    }
}