package ru.ermakov.feature_todo_impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ermakov.core.network_manager.NetworkManager
import ru.ermakov.database.data.ToDoDatabase
import ru.ermakov.feature_todo_api.data.local.dao.ToDoDao
import ru.ermakov.feature_todo_api.domain.use_case.ChangeDoneByToDoIdUseCase
import ru.ermakov.feature_todo_api.domain.use_case.DeleteToDoByIdUseCase
import ru.ermakov.feature_todo_api.domain.use_case.GetToDoByIdUseCase
import ru.ermakov.feature_todo_api.domain.use_case.GetToDosUseCase
import ru.ermakov.feature_todo_api.domain.use_case.SaveToDoUseCase
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoLocalDataSource
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoLocalDataSourceImpl
import ru.ermakov.feature_todo_impl.data.repository.ToDoRepositoryImpl
import ru.ermakov.feature_todo_impl.domain.repository.ToDoRepository
import ru.ermakov.feature_todo_impl.domain.use_case.ChangeDoneByToDoIdUseCaseImpl
import ru.ermakov.feature_todo_impl.domain.use_case.DeleteToDoByIdUseCaseImpl
import ru.ermakov.feature_todo_impl.domain.use_case.GetToDoByIdUseCaseImpl
import ru.ermakov.feature_todo_impl.domain.use_case.GetToDosUseCaseImpl
import ru.ermakov.feature_todo_impl.domain.use_case.SaveToDoUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToDoModule {
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

    @Singleton
    @Provides
    fun provideToDoRepository(
        toDoLocalDataSource: ToDoLocalDataSource,
        networkManager: NetworkManager
    ): ToDoRepository {
        return ToDoRepositoryImpl(
            toDoLocalDataSource = toDoLocalDataSource,
            networkManager = networkManager
        )
    }

    @Provides
    fun provideGetToDosUseCase(toDoRepository: ToDoRepository): GetToDosUseCase {
        return GetToDosUseCaseImpl(toDoRepository = toDoRepository)
    }

    @Provides
    fun provideChangeDoneByToDoIdUseCase(toDoRepository: ToDoRepository): ChangeDoneByToDoIdUseCase {
        return ChangeDoneByToDoIdUseCaseImpl(toDoRepository = toDoRepository)
    }

    @Provides
    fun provideGetToDoByIdUseCase(toDoRepository: ToDoRepository): GetToDoByIdUseCase {
        return GetToDoByIdUseCaseImpl(toDoRepository = toDoRepository)
    }

    @Provides
    fun provideSaveToDoUseCase(toDoRepository: ToDoRepository): SaveToDoUseCase {
        return SaveToDoUseCaseImpl(toDoRepository = toDoRepository)
    }

    @Provides
    fun provideDeleteToDoByIdUseCase(toDoRepository: ToDoRepository): DeleteToDoByIdUseCase {
        return DeleteToDoByIdUseCaseImpl(toDoRepository = toDoRepository)
    }
}