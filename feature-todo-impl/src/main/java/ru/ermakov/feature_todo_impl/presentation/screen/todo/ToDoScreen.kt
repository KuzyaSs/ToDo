package ru.ermakov.feature_todo_impl.presentation.screen.todo

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo_impl.R
import ru.ermakov.feature_todo_impl.presentation.screen.todo.component.DeadlineSelector
import ru.ermakov.feature_todo_impl.presentation.screen.todo.component.PrioritySelector
import ru.ermakov.feature_todo_impl.presentation.screen.todo.component.ToDoContentTextField
import ru.ermakov.feature_todo_impl.presentation.screen.todo.component.ToDoTopAppBar
import ru.ermakov.feature_todo_impl.presentation.utils.toStringToDoError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreen(
    state: ToDoState,
    effect: ToDoEffect?,
    onEvent: (ToDoEvent) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(key1 = effect) {
        when (effect) {
            ToDoEffect.OnNavigateBack -> onNavigateBack()
            is ToDoEffect.ShowSnackBarErrorMessage -> {
                scope.launch {
                    hostState.showSnackbar(message = effect.error.toStringToDoError(context = context))
                }
            }
            null -> Unit
        }
    }

    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            ToDoTopAppBar(
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                isSaveAvailable = state.content.isNotBlank() && !state.isSaving,
                onCloseClick = { onEvent(ToDoEvent.OnCloseClick) },
                onSaveClick = { onEvent(ToDoEvent.OnSaveClick) }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = hostState,
                snackbar = { data ->
                    Snackbar(
                        data,
                        containerColor = ToDoTheme.colors.backSecondary,
                        contentColor = ToDoTheme.colors.labelPrimary
                    )
                }
            )
        },
        containerColor = ToDoTheme.colors.backPrimary,
    ) { padding ->
        if (state.isLoading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = ToDoTheme.colors.blue)
            }
        } else if (state.error != null) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ) {
                Text(
                    text = state.error.toStringToDoError(context = context),
                    style = ToDoTheme.typography.body,
                    modifier = Modifier.padding(all = ToDoTheme.size.medium)
                )
                Button(
                    onClick = { onEvent(ToDoEvent.OnRetryClick) },
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = ToDoTheme.colors.blue)
                ) {
                    Text(
                        text = stringResource(id = R.string.retry),
                        style = ToDoTheme.typography.button,
                        color = ToDoTheme.colors.white
                    )
                }
            }
        } else {
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
                    onMenuClick = { onEvent(ToDoEvent.OnPriorityMenuOpen) },
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
                        .clickable(
                            enabled = state.toDo != null,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                        ) { onEvent(ToDoEvent.OnDeleteClick) }
                        .padding(vertical = ToDoTheme.size.medium)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete_to_do),
                        tint = if (state.toDo != null) ToDoTheme.colors.red
                        else ToDoTheme.colors.labelDisable
                    )
                    Text(
                        text = stringResource(id = R.string.delete),
                        style = ToDoTheme.typography.body.copy(
                            color = if (state.toDo != null) ToDoTheme.colors.red
                            else ToDoTheme.colors.labelDisable
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
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