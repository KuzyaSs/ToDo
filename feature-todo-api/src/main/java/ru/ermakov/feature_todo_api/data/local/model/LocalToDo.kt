package ru.ermakov.feature_todo_api.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.ermakov.feature_todo_api.domain.model.Priority

@Entity(tableName = "todo")
data class LocalToDo(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "priority")
    val priority: Priority,
    @ColumnInfo(name = "is_done")
    val isDone: Boolean,
    @ColumnInfo(name = "creation_date")
    val creationDate: Long,
    @ColumnInfo(name = "modification_date")
    val modificationDate: Long?,
    @ColumnInfo(name = "deadline")
    val deadline: Long?
)
