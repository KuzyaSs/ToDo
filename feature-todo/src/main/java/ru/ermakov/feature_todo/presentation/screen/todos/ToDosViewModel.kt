package ru.ermakov.feature_todo.presentation.screen.todos

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
import ru.ermakov.core.domain.model.Result
import ru.ermakov.feature_todo.domain.repository.ToDoRepository
import javax.inject.Inject

@HiltViewModel
class ToDosViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ToDosState())
    val state: StateFlow<ToDosState> = _state.asStateFlow()

    private val _effect = Channel<ToDosEffect>()
    val effect: Flow<ToDosEffect> = _effect.receiveAsFlow()

    init {
        viewModelScope.launch {
            toDoRepository.getToDos().collect { toDosResult ->
                when (toDosResult) {
                    is Result.Success -> {
                        _state.update { state ->
                            state.copy(
                                toDos = toDosResult.data,
                                numOfDoneToDos = toDosResult.data.count { toDo -> toDo.isDone }
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update { state -> state.copy(error = toDosResult.error) }
                    }
                }
            }
        }
    }

    fun obtainEvent(event: ToDosEvent) {
        when (event) {
            is ToDosEvent.OnToDoItemClick -> navigateToToDoDestination(toDoId = event.toDoId)

            is ToDosEvent.OnDoneToDoVisibilityClick -> changeDoneToDoVisibility()

            is ToDosEvent.OnAddClick -> navigateToToDoDestination(toDoId = null)

            is ToDosEvent.OnNewClick -> navigateToToDoDestination(toDoId = null)

            is ToDosEvent.OnDoneChange -> changeDoneByToDoId(
                toDoId = event.toDoId,
                isDone = event.isDone
            )
        }
    }

    private fun navigateToToDoDestination(toDoId: String?) {
        viewModelScope.launch {
            _effect.send(ToDosEffect.OnNavigateToToDoDestination(toDoId = toDoId))
        }
    }

    private fun changeDoneToDoVisibility() {
        _state.update { state -> state.copy(isDoneToDoVisible = !state.isDoneToDoVisible) }
    }

    private fun changeDoneByToDoId(toDoId: String, isDone: Boolean) {
        viewModelScope.launch {
            toDoRepository.changeDoneByToDoId(toDoId = toDoId, isDone = isDone)
        }
    }
}