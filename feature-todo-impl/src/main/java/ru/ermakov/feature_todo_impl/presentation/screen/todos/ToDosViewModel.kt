package ru.ermakov.feature_todo_impl.presentation.screen.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ermakov.core.error.CoreError
import ru.ermakov.core.result.Result
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_api.domain.use_case.ChangeDoneByToDoIdUseCase
import ru.ermakov.feature_todo_api.domain.use_case.GetToDosUseCase
import javax.inject.Inject

/**
 * Controls UI state of ToDosScreen
 */
@HiltViewModel
class ToDosViewModel @Inject constructor(
    private val getToDosUseCase: GetToDosUseCase,
    private val changeDoneByToDoIdUseCase: ChangeDoneByToDoIdUseCase,
) :
    ViewModel() {
    private val _state = MutableStateFlow(ToDosState())
    val state: StateFlow<ToDosState> = _state.asStateFlow()

    private val _effect = Channel<ToDosEffect?>()
    val effect: Flow<ToDosEffect?> = _effect.receiveAsFlow()

    init {
        getToDos()
    }

    fun obtainEvent(event: ToDosEvent) {
        when (event) {
            is ToDosEvent.OnToDoItemClick -> navigateToToDoDestination(toDoId = event.toDoId)

            is ToDosEvent.OnDoneToDoVisibilityClick -> changeDoneToDoVisibility()

            is ToDosEvent.OnAddClick -> navigateToToDoDestination(toDoId = null)

            is ToDosEvent.OnNewClick -> navigateToToDoDestination(toDoId = null)

            is ToDosEvent.OnDoneChange -> changeDoneByToDoId(
                toDo = event.toDo,
                isDone = event.isDone
            )
        }
    }

    private fun getToDos() {
        viewModelScope.launch {
            getToDosUseCase().collect { toDosResult ->
                when (toDosResult) {
                    is Result.Success -> {
                        _state.update { state ->
                            state.copy(
                                toDos = toDosResult.data,
                                numOfDoneToDos = toDosResult.data.count { toDo -> toDo.isDone },
                                isOfflineMode = false,
                                isLoading = false,
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update { state ->
                            val toDos = toDosResult.data ?: emptyList()
                            state.copy(
                                toDos = toDos,
                                numOfDoneToDos = toDos.count { toDo -> toDo.isDone },
                                isOfflineMode = toDosResult.error == CoreError.OFFLINE_MODE,
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun changeDoneToDoVisibility() {
        _state.update { state -> state.copy(isDoneToDoVisible = !state.isDoneToDoVisible) }
    }

    private fun changeDoneByToDoId(toDo: ToDo, isDone: Boolean) {
        viewModelScope.launch {
            changeDoneByToDoIdUseCase(toDo, isDone)
        }
    }

    private fun navigateToToDoDestination(toDoId: String?) {
        viewModelScope.launch {
            _effect.send(ToDosEffect.OnNavigateToToDoDestination(toDoId = toDoId))
        }
    }
}