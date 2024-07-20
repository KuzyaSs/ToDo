package ru.ermakov.database.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ermakov.database.data.ToDoDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("ru.ermakov.todo.settings") }
        )
    }

    @Singleton
    @Provides
    fun provideToDoDatabase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = ToDoDatabase::class.java,
            name = ToDoDatabase::class.simpleName
        ).build()
    }
}