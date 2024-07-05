package ru.ermakov.feature_todo_impl.data.local.data_source

import android.content.SharedPreferences
import android.util.Log
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

private const val TO_DO_SYNC_DATE = "TO_DO_SYNC_DATE"
private const val SYNC_DIFFERENCE = 10L

class ToDoSyncDateLocalDataSourceImpl(
    private val sharedPreferences: SharedPreferences
) : ToDoSyncDateLocalDataSource {
    override suspend fun setToDoSyncDateToCurrentDate() {
        val date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        Log.d("MY_TAG", "SET SYNC: ${date}")
        sharedPreferences.edit().apply {
            putLong(
                TO_DO_SYNC_DATE,
                date.toInstant(TimeZone.currentSystemDefault()).epochSeconds - SYNC_DIFFERENCE
            )
        }.apply()
    }

    override suspend fun getToDoSyncDate(): LocalDateTime {
        sharedPreferences.apply {
            return Instant.fromEpochSeconds(
                getLong(TO_DO_SYNC_DATE, 0L)
            ).toLocalDateTime(TimeZone.currentSystemDefault())
        }
    }
}