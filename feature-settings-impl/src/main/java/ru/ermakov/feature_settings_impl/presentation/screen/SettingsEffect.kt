package ru.ermakov.feature_settings_impl.presentation.screen

sealed interface SettingsEffect {
    data object OnNavigateBack : SettingsEffect
}