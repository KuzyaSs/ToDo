package ru.ermakov.feature_todo_impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.ermakov.feature_todo_impl.data.remote.api.ToDoApi
import ru.ermakov.feature_todo_impl.data.remote.data_source.ToDoRemoteDataSource
import ru.ermakov.feature_todo_impl.data.remote.data_source.ToDoRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToDoRemoteModule {
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
}