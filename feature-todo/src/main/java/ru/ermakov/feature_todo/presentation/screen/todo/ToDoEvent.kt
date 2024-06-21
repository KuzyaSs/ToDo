package ru.ermakov.feature_todo.presentation.screen.todo

import kotlinx.datetime.LocalDate
import ru.ermakov.feature_todo.domain.model.Priority

sealed interface ToDoEvent {
    data class OnEnterScreen(val toDoId: String?) : ToDoEvent
    data object OnCloseClick : ToDoEvent
    data object OnSaveClick : ToDoEvent
    data class OnContentChange(val content: String) : ToDoEvent
    data class OnPriorityChange(val priority: Priority) : ToDoEvent
    data object OnPriorityMenuClick : ToDoEvent
    data object OnPriorityMenuDismiss : ToDoEvent
    data class OnDeadlineChange(val deadline: LocalDate?) : ToDoEvent
    data class OnDeadlineSwitchChange(val isChecked: Boolean) : ToDoEvent
    data object OnDatePickerDismiss : ToDoEvent
    data object OnDeleteClick : ToDoEvent
}