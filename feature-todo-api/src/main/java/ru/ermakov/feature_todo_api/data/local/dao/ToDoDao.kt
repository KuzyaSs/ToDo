package ru.ermakov.feature_todo_api.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.ermakov.feature_todo_api.data.local.model.LocalToDo

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo ORDER BY modification_date DESC")
    fun getToDos(): Flow<List<LocalToDo>>

    @Query("SELECT * FROM todo WHERE id = :toDoId")
    suspend fun getToDoById(toDoId: String): LocalToDo?

    @Upsert
    suspend fun upsertToDos(localToDos: List<LocalToDo>)

    @Query("DELETE FROM todo WHERE id = :toDoId")
    suspend fun deleteToDoById(toDoId: String)
}