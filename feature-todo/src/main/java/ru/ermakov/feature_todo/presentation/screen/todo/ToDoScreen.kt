package ru.ermakov.feature_todo.presentation.screen.todo

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo.R
import ru.ermakov.feature_todo.presentation.screen.todo.component.DeadlineSelector
import ru.ermakov.feature_todo.presentation.screen.todo.component.PrioritySelector
import ru.ermakov.feature_todo.presentation.screen.todo.component.ToDoContentTextField
import ru.ermakov.feature_todo.presentation.screen.todo.component.ToDoTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreen(
    state: ToDoState,
    effect: ToDoEffect?,
    onEvent: (ToDoEvent) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFirstEnterScreen by rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(isFirstEnterScreen) {
        if (isFirstEnterScreen) {
            onEvent(ToDoEvent.OnEnterScreen(toDoId = state.toDoId))
            isFirstEnterScreen = false
        }
    }
    LaunchedEffect(key1 = effect) {
        when (effect) {
            ToDoEffect.OnNavigateBack -> onNavigateBack()
            null -> Unit
        }
    }

    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            ToDoTopAppBar(
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                isSaveAvailable = state.content.isNotBlank(),
                onCloseClick = { onEvent(ToDoEvent.OnCloseClick) },
                onSaveClick = { onEvent(ToDoEvent.OnSaveClick) }
            )
        },
        containerColor = ToDoTheme.colors.backPrimary
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    start = ToDoTheme.size.medium,
                    end = ToDoTheme.size.medium
                )
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
        ) {
            Spacer(modifier = Modifier.height(ToDoTheme.size.medium))
            ToDoContentTextField(
                content = state.content,
                onValueChange = { content -> onEvent(ToDoEvent.OnContentChange(content = content)) }
            )
            PrioritySelector(
                priority = state.priority,
                isMenuVisible = state.isPriorityMenuVisible,
                onMenuClick = { onEvent(ToDoEvent.OnPriorityMenuClick) },
                onDismissRequest = { onEvent(ToDoEvent.OnPriorityMenuDismiss) },
                onItemClick = { priority -> onEvent(ToDoEvent.OnPriorityChange(priority = priority)) }
            )
            HorizontalDivider(thickness = 0.5.dp, color = ToDoTheme.colors.supportSeparator)
            DatePicker(
                currentDateTime = state.currentDateTime,
                isDatePickerVisible = state.isDatePickerVisible,
                onDeadlineChange = { deadline -> onEvent(ToDoEvent.OnDeadlineChange(deadline = deadline)) },
                onDatePickerDismiss = { onEvent(ToDoEvent.OnDatePickerDismiss) }
            )
            DeadlineSelector(
                deadline = state.deadline,
                onDeadlineSwitchChange = { isChecked ->
                    onEvent(ToDoEvent.OnDeadlineSwitchChange(isChecked = isChecked))
                }
            )
            HorizontalDivider(thickness = 0.5.dp, color = ToDoTheme.colors.supportSeparator)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = ToDoTheme.size.medium)
                    .clickable(enabled = state.toDoId != null) { onEvent(ToDoEvent.OnDeleteClick) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete_to_do),
                    tint = if (state.toDoId != null) ToDoTheme.colors.red
                    else ToDoTheme.colors.labelDisable
                )
                Text(
                    text = stringResource(id = R.string.delete),
                    style = ToDoTheme.typography.body.copy(
                        color = if (state.toDoId != null) ToDoTheme.colors.red
                        else ToDoTheme.colors.labelDisable
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun ToDoScreenPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary, modifier = Modifier.fillMaxSize()) {
            ToDoScreen(state = ToDoState(), effect = null, onEvent = {}, onNavigateBack = {})
        }
    }
}

@Composable
private fun DatePicker(
    currentDateTime: LocalDateTime,
    isDatePickerVisible: Boolean,
    onDeadlineChange: (deadline: LocalDate) -> Unit,
    onDatePickerDismiss: () -> Unit
) {
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        R.style.ToDoDatePicker,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            onDeadlineChange(LocalDate(year = year, month = Month(month + 1), dayOfMonth = day))
        },
        currentDateTime.year,
        currentDateTime.monthNumber - 1,
        currentDateTime.dayOfMonth
    )
    datePickerDialog.setOnCancelListener { onDatePickerDismiss() }
    datePickerDialog.setOnDismissListener { onDatePickerDismiss() }
    if (isDatePickerVisible) {
        datePickerDialog.show()
    } else {
        datePickerDialog.hide()
    }
}