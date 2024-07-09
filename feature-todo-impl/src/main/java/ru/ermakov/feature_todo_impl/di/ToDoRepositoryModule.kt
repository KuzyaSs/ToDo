package ru.ermakov.feature_todo_impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ermakov.core.network_manager.NetworkManager
import ru.ermakov.feature_todo_impl.data.local.data_source.DeviceLocalDataSource
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoLocalDataSource
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoSyncDateLocalDataSource
import ru.ermakov.feature_todo_impl.data.remote.data_source.ToDoRemoteDataSource
import ru.ermakov.feature_todo_impl.data.repository.ToDoRepositoryImpl
import ru.ermakov.feature_todo_impl.domain.repository.ToDoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToDoRepositoryModule {
    @Singleton
    @Provides
    fun provideToDoRepository(
        toDoLocalDataSource: ToDoLocalDataSource,
        toDoRemoteDataSource: ToDoRemoteDataSource,
        toDoSyncDateLocalDataSource: ToDoSyncDateLocalDataSource,
        deviceLocalDataSource: DeviceLocalDataSource,
        networkManager: NetworkManager
    ): ToDoRepository {
        return ToDoRepositoryImpl(
            toDoLocalDataSource = toDoLocalDataSource,
            toDoRemoteDataSource = toDoRemoteDataSource,
            toDoSyncDateLocalDataSource = toDoSyncDateLocalDataSource,
            deviceLocalDataSource = deviceLocalDataSource,
            networkManager = networkManager
        )
    }
}