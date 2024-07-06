package ru.ermakov.feature_todo_impl.data.local.data_source

import kotlinx.datetime.LocalDateTime
/**
 * Controls the synchronization date.
 */
interface ToDoSyncDateLocalDataSource {
    suspend fun setToDoSyncDateToCurrentDate()
    suspend fun getToDoSyncDate(): LocalDateTime
}