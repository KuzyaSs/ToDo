package ru.ermakov.feature_todo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ermakov.feature_todo.data.repository.ToDoRepositoryImpl
import ru.ermakov.feature_todo.domain.repository.ToDoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToDoModule {
    @Singleton
    @Provides
    fun provideToDoRepository(): ToDoRepository {
        return ToDoRepositoryImpl()
    }
}