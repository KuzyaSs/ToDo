package ru.ermakov.feature_todo_impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.ermakov.feature_todo_api.domain.use_case.ChangeDoneByToDoIdUseCase
import ru.ermakov.feature_todo_api.domain.use_case.DeleteToDoByIdUseCase
import ru.ermakov.feature_todo_api.domain.use_case.GetToDoByIdUseCase
import ru.ermakov.feature_todo_api.domain.use_case.GetToDosUseCase
import ru.ermakov.feature_todo_api.domain.use_case.SaveToDoUseCase
import ru.ermakov.feature_todo_impl.domain.repository.ToDoRepository
import ru.ermakov.feature_todo_impl.domain.use_case.ChangeDoneByToDoIdUseCaseImpl
import ru.ermakov.feature_todo_impl.domain.use_case.DeleteToDoByIdUseCaseImpl
import ru.ermakov.feature_todo_impl.domain.use_case.GetToDoByIdUseCaseImpl
import ru.ermakov.feature_todo_impl.domain.use_case.GetToDosUseCaseImpl
import ru.ermakov.feature_todo_impl.domain.use_case.SaveToDoUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
object ToDoUseCaseModule {
    @ViewModelScoped
    @Provides
    fun provideGetToDosUseCase(toDoRepository: ToDoRepository): GetToDosUseCase {
        return GetToDosUseCaseImpl(toDoRepository = toDoRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideChangeDoneByToDoIdUseCase(toDoRepository: ToDoRepository): ChangeDoneByToDoIdUseCase {
        return ChangeDoneByToDoIdUseCaseImpl(toDoRepository = toDoRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideGetToDoByIdUseCase(toDoRepository: ToDoRepository): GetToDoByIdUseCase {
        return GetToDoByIdUseCaseImpl(toDoRepository = toDoRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideSaveToDoUseCase(toDoRepository: ToDoRepository): SaveToDoUseCase {
        return SaveToDoUseCaseImpl(toDoRepository = toDoRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideDeleteToDoByIdUseCase(toDoRepository: ToDoRepository): DeleteToDoByIdUseCase {
        return DeleteToDoByIdUseCaseImpl(toDoRepository = toDoRepository)
    }
}