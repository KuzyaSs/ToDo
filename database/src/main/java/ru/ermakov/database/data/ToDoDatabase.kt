package ru.ermakov.database.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ermakov.feature_todo_api.data.local.dao.ToDoDao
import ru.ermakov.feature_todo_api.data.local.model.LocalToDo

@Database(entities = [LocalToDo::class], version = 1)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun getToDoDao(): ToDoDao
}