package ru.ermakov.feature_todo_impl.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.ermakov.core.network_manager.NetworkManager
import ru.ermakov.database.data.ToDoDatabase
import ru.ermakov.feature_todo_api.data.local.dao.ToDoDao
import ru.ermakov.feature_todo_api.domain.use_case.ChangeDoneByToDoIdUseCase
import ru.ermakov.feature_todo_api.domain.use_case.DeleteToDoByIdUseCase
import ru.ermakov.feature_todo_api.domain.use_case.GetToDoByIdUseCase
import ru.ermakov.feature_todo_api.domain.use_case.GetToDosUseCase
import ru.ermakov.feature_todo_api.domain.use_case.SaveToDoUseCase
import ru.ermakov.feature_todo_impl.data.local.data_source.DeviceLocalDataSource
import ru.ermakov.feature_todo_impl.data.local.data_source.DeviceLocalDataSourceImpl
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoSyncDateLocalDataSource
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoLocalDataSource
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoLocalDataSourceImpl
import ru.ermakov.feature_todo_impl.data.local.data_source.ToDoSyncDateLocalDataSourceImpl
import ru.ermakov.feature_todo_impl.data.remote.api.ToDoApi
import ru.ermakov.feature_todo_impl.data.remote.data_source.ToDoRemoteDataSource
import ru.ermakov.feature_todo_impl.data.remote.data_source.ToDoRemoteDataSourceImpl
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

    @Singleton
    @Provides
    fun provideToDoApi(retrofit: Retrofit): ToDoApi {
        return retrofit.create(ToDoApi::class.java)
    }

    @Singleton
    @Provides
    fun provideToDoRemoteDataSource(toDoApi: ToDoApi): ToDoRemoteDataSource {
        return ToDoRemoteDataSourceImpl(toDoApi = toDoApi)
    }

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