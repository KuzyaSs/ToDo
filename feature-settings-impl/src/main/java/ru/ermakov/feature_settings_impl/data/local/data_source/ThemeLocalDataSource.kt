package ru.ermakov.feature_settings_impl.data.local.data_source

import kotlinx.coroutines.flow.Flow
import ru.ermakov.feature_settings_impl.domain.model.Theme

interface ThemeLocalDataSource {
    suspend fun setTheme(theme: Theme)
    fun getTheme(): Flow<Theme>
}