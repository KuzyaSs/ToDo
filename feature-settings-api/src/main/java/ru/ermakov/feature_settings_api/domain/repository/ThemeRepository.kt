package ru.ermakov.feature_settings_api.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.ermakov.feature_settings_impl.domain.model.Theme

interface ThemeRepository {
    suspend fun setTheme(theme: Theme)
    fun getTheme(): Flow<Theme>
}