package ru.ermakov.feature_todo_impl.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.ermakov.feature_settings_impl.R
import ru.ermakov.feature_settings_impl.domain.model.Theme

@Composable
fun Theme.toStringTheme(): String {
    return when (this) {
        Theme.LIGHT -> stringResource(id = R.string.light_theme)
        Theme.DARK -> stringResource(id = R.string.dark_theme)
        Theme.SYSTEM -> stringResource(id = R.string.system_theme)
    }
}