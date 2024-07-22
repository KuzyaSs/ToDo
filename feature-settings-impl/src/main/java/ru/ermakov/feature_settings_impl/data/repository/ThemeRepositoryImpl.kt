package ru.ermakov.feature_settings_impl.data.repository

import kotlinx.coroutines.flow.Flow
import ru.ermakov.feature_settings_api.domain.repository.ThemeRepository
import ru.ermakov.feature_settings_impl.data.local.data_source.ThemeLocalDataSource
import ru.ermakov.feature_settings_impl.domain.model.Theme

class ThemeRepositoryImpl(
    private val themeLocalDataSource: ThemeLocalDataSource,
) : ThemeRepository {
    override suspend fun setTheme(theme: Theme) {
        themeLocalDataSource.setTheme(theme = theme)
    }

    override fun getTheme(): Flow<Theme> {
        return themeLocalDataSource.getTheme()
    }
}