package ru.ermakov.feature_todo.presentation.screen.todo

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
import kotlinx.datetime.LocalDate
import ru.ermakov.core.domain.model.Result
import ru.ermakov.feature_todo.domain.model.Priority
import ru.ermakov.feature_todo.domain.model.ToDoRequest
import ru.ermakov.feature_todo.domain.repository.ToDoRepository
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(private val toDoRepository: ToDoRepository) : ViewModel() {
    private val _state = MutableStateFlow(ToDoState())
    val state: StateFlow<ToDoState> = _state.asStateFlow()

    private val _effect = Channel<ToDoEffect>()
    val effect: Flow<ToDoEffect> = _effect.receiveAsFlow()

    fun obtainEvent(event: ToDoEvent) {
        when (event) {
            is ToDoEvent.OnEnterScreen -> getToDoById(toDoId = event.toDoId)
            is ToDoEvent.OnCloseClick -> navigateBack()
            is ToDoEvent.OnSaveClick -> saveToDo()
            is ToDoEvent.OnContentChange -> changeContent(content = event.content)
            is ToDoEvent.OnPriorityChange -> changePriority(priority = event.priority)
            is ToDoEvent.OnPriorityMenuClick -> showPriorityMenu()
            is ToDoEvent.OnPriorityMenuDismiss -> hidePriorityMenu()
            is ToDoEvent.OnDeadlineChange -> changeDeadline(deadline = event.deadline)
            is ToDoEvent.OnDeadlineSwitchChange -> changeDeadlineSwitch(isChecked = event.isChecked)
            is ToDoEvent.OnDatePickerDismiss -> hideDatePicker()
            is ToDoEvent.OnDeleteClick -> deleteToDo()
        }
    }

    private fun getToDoById(toDoId: String?) {
        if (toDoId == null) {
            return
        }
        viewModelScope.launch {
            when (val toDoResult = toDoRepository.getToDoById(toDoId = toDoId)) {
                is Result.Success -> _state.update { state ->
                    state.copy(
                        toDoId = toDoResult.data.id,
                        content = toDoResult.data.content,
                        priority = toDoResult.data.priority,
                        deadline = toDoResult.data.deadline,
                        isDeadlineSwitchChecked = toDoResult.data.deadline != null
                    )
                }

                is Result.Error -> state
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.send(ToDoEffect.OnNavigateBack)
        }
    }

    private fun saveToDo() {
        viewModelScope.launch {
            _state.value.apply {
                val toDoRequest = ToDoRequest(
                    id = toDoId,
                    content = content,
                    priority = priority,
                    deadline = deadline
                )
                if (toDoId != null) {
                    toDoRepository.updateTodo(toDoRequest = toDoRequest)
                } else {
                    toDoRepository.insertTodo(toDoRequest = toDoRequest)
                }
            }
            _effect.send(ToDoEffect.OnNavigateBack)
        }
    }

    private fun changeContent(content: String) {
        _state.update { state -> state.copy(content = content) }
    }

    private fun changePriority(priority: Priority) {
        _state.update { state -> state.copy(priority = priority, isPriorityMenuVisible = false) }
    }

    private fun showPriorityMenu() {
        _state.update { state -> state.copy(isPriorityMenuVisible = true) }
    }

    private fun hidePriorityMenu() {
        _state.update { state -> state.copy(isPriorityMenuVisible = false) }
    }

    private fun changeDeadline(deadline: LocalDate?) {
        _state.update { state -> state.copy(deadline = deadline) }
        hideDatePicker()
    }

    private fun changeDeadlineSwitch(isChecked: Boolean) {
        if (isChecked) {
            showDatePicker()
        } else {
            _state.update { state -> state.copy(deadline = null, isDeadlineSwitchChecked = false) }
        }
    }

    private fun showDatePicker() {
        _state.update { state -> state.copy(isDatePickerVisible = true) }
    }

    private fun hideDatePicker() {
        _state.update { state -> state.copy(isDatePickerVisible = false) }
    }

    private fun deleteToDo() {
        viewModelScope.launch {
            _state.value.toDoId?.let { toDoId ->
                toDoRepository.deleteToDoById(toDoId = toDoId)
            }
            _effect.send(ToDoEffect.OnNavigateBack)
        }
    }
}