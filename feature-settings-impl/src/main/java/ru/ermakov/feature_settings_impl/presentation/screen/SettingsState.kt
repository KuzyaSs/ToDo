package ru.ermakov.feature_settings_impl.presentation.screen

import ru.ermakov.feature_settings_impl.domain.model.Theme

data class SettingsState(
    val theme: Theme = Theme.SYSTEM,
    val isThemeMenuVisible: Boolean = false,
)