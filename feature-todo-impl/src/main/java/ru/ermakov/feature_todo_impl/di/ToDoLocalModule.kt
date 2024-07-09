package ru.ermakov.feature_todo_impl.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ermakov.database.data.ToDoDatabase
import ru.ermakov.feature_todo_api.data.local.dao.ToDoDao
import ru.ermakov.feature_todo_impl.data.local.data_source.DeviceLocalDataSource
import ru.ermakov.feature_todo_impl.data.local.data_source.DeviceLocalDataSourceImpl
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoLocalDataSource
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoLocalDataSourceImpl
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoSyncDateLocalDataSource
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoSyncDateLocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToDoLocalModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("ru.ermakov.todo", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideToDoSyncDateLocalDataSource(
        sharedPreferences: SharedPreferences
    ): ToDoSyncDateLocalDataSource {
        return ToDoSyncDateLocalDataSourceImpl(sharedPreferences = sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideDeviceLocalDataSource(
        @ApplicationContext context: Context
    ): DeviceLocalDataSource {
        return DeviceLocalDataSourceImpl(context = context)
    }

    @Singleton
    @Provides
    fun provideToDoDao(toDoDatabase: ToDoDatabase): ToDoDao {
        return toDoDatabase.getToDoDao()
    }

    @Singleton
    @Provides
    fun provideToDoLocalDataSource(toDoDao: ToDoDao): ToDoLocalDataSource {
        return ToDoLocalDataSourceImpl(toDoDao = toDoDao)
    }
}