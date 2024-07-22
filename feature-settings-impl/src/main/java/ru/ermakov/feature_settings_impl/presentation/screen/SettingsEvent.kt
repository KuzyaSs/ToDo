package ru.ermakov.feature_settings_impl.presentation.screen

import ru.ermakov.feature_settings_impl.domain.model.Theme

sealed interface SettingsEvent {
    data object OnBackClick : SettingsEvent
    data object OnThemeMenuOpen : SettingsEvent
    data object OnThemeMenuDismiss : SettingsEvent
    data class OnThemeChange(val theme: Theme) : SettingsEvent
}