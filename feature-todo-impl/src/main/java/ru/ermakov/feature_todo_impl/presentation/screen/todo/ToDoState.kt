package ru.ermakov.feature_todo_impl.presentation.screen.todo

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.ermakov.core.error.RootError
import ru.ermakov.feature_todo_api.domain.model.Priority
import ru.ermakov.feature_todo_api.domain.model.ToDo

/**
 * UI state of ToDoScreen.
 */
data class ToDoState(
    val toDoId: String = "",
    val toDo: ToDo? = null,
    val content: String = "",
    val priority: Priority = Priority.NORMAL,
    val isPriorityMenuVisible: Boolean = false,
    val currentDateTime: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val deadline: LocalDate? = null,
    val isDeadlineSwitchChecked: Boolean = false,
    val isDatePickerVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: RootError? = null
)