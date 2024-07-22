package ru.ermakov.feature_todo_impl.data.local.data_source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private const val TO_DO_SYNC_DATE_KEY = "TO_DO_SYNC_DATE_KEY"
private const val SYNC_DIFFERENCE = 10L

class ToDoSyncDateLocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : ToDoSyncDateLocalDataSource {
    override suspend fun getToDoSyncDate(): LocalDateTime {
        val toDoSyncDateKey = longPreferencesKey(TO_DO_SYNC_DATE_KEY)
        val preferences = dataStore.data.first()
        return Instant.fromEpochSeconds(preferences[toDoSyncDateKey] ?: 0)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    }

    override suspend fun setToDoSyncDateToCurrentDate() {
        val toDoSyncDateKey = longPreferencesKey(TO_DO_SYNC_DATE_KEY)
        dataStore.edit { preferences ->
            preferences[toDoSyncDateKey] = Clock.System.now().epochSeconds - SYNC_DIFFERENCE
        }
    }
}