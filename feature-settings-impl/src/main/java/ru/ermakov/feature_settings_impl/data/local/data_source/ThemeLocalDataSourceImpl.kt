package ru.ermakov.feature_settings_impl.data.local.data_source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ermakov.feature_settings_impl.domain.model.Theme

private const val THEME_KEY = "THEME_KEY"

class ThemeLocalDataSourceImpl(private val dataStore: DataStore<Preferences>) : ThemeLocalDataSource {
    override fun getTheme(): Flow<Theme> {
        val themeKey = stringPreferencesKey(THEME_KEY)
        return dataStore.data.map { preferences ->
            val theme = preferences[themeKey] ?: return@map Theme.SYSTEM
            return@map Theme.valueOf(theme)
        }
    }

    override suspend fun setTheme(theme: Theme) {
        val themeKey = stringPreferencesKey(THEME_KEY)
        dataStore.edit { preferences ->
            preferences[themeKey] = theme.name
        }
    }
}