package ru.ermakov.feature_todo_impl.presentation.screen.todos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo_impl.R
import ru.ermakov.feature_todo_api.domain.model.Priority
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_impl.presentation.screen.todos.component.ToDoList
import ru.ermakov.feature_todo_impl.presentation.utils.toStringToDoError

@Composable
fun ToDosScreen(
    state: ToDosState,
    effect: ToDosEffect?,
    onEvent: (ToDosEvent) -> Unit,
    onNavigateToToDoDestination: (toDoId: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(key1 = effect) {
        when (effect) {
            is ToDosEffect.OnNavigateToToDoDestination -> onNavigateToToDoDestination(effect.toDoId)
            is ToDosEffect.ShowSnackBarErrorMessage -> {
                scope.launch {
                    hostState.showSnackbar(message = effect.error.toStringToDoError(context = context))
                }
            }
            null -> Unit
        }
    }
    Scaffold(
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ToDosEvent.OnAddClick) },
                containerColor = ToDoTheme.colors.blue,
                contentColor = ToDoTheme.colors.white
            ) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.add_to_do))
            }
        },
        containerColor = ToDoTheme.colors.backPrimary
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(all = ToDoTheme.size.medium)
        ) {
            Text(
                text = stringResource(id = R.string.my_to_do),
                style = ToDoTheme.typography.largeTitle,
                modifier = Modifier.padding(
                    start = ToDoTheme.size.large,
                    top = ToDoTheme.size.large,
                    bottom = ToDoTheme.size.small
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = ToDoTheme.size.large,
                        end = ToDoTheme.size.large,
                        bottom = ToDoTheme.size.medium
                    )
            ) {
                Text(
                    text = stringResource(
                        id = R.string.num_of_done_to_do,
                        state.numOfDoneToDos
                    ),
                    style = ToDoTheme.typography.body.copy(color = ToDoTheme.colors.labelTertiary),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (state.isDoneToDoVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = stringResource(id = R.string.change_done_to_do_visibility),
                    tint = ToDoTheme.colors.blue,
                    modifier = Modifier.clickable { onEvent(ToDosEvent.OnDoneToDoVisibilityClick) }
                )
            }
            if (state.isLoading) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(color = ToDoTheme.colors.blue)
                }
            } else {
                ToDoList(
                    toDos = if (state.isDoneToDoVisible) state.toDos else state.toDos.filter { toDo -> !toDo.isDone },
                    onToDoItemClick = { toDoId ->
                        onEvent(ToDosEvent.OnToDoItemClick(toDoId = toDoId))
                    },
                    onDoneChange = { toDo, isDone ->
                        onEvent(ToDosEvent.OnDoneChange(toDo = toDo, isDone = isDone))
                    },
                    onNewClick = { onEvent(ToDosEvent.OnNewClick) }
                )
            }
        }
    }
}

@Preview
@Composable
fun ToDosScreenPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary, modifier = Modifier.fillMaxSize()) {
            ToDosScreen(
                state = ToDosState(
                    toDos = buildList {
                        repeat(50) {
                            add(
                                ToDo(
                                    "1",
                                    "Some content",
                                    Priority.URGENT,
                                    true,
                                    Clock.System.now()
                                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                                    null,
                                    null
                                )
                            )
                            add(
                                ToDo(
                                    "2",
                                    "Some content",
                                    Priority.URGENT,
                                    false,
                                    Clock.System.now()
                                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                                    null,
                                    null
                                )
                            )
                            add(
                                ToDo(
                                    "3",
                                    "Some content Some content Some content Some content Some content Some content Some content Some content Some content Some content Some content Some content Some content Some content ",
                                    Priority.NORMAL,
                                    true,
                                    Clock.System.now()
                                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                                    null,
                                    null
                                )
                            )
                            add(
                                ToDo(
                                    "4",
                                    "Some content Some content Some content ",
                                    Priority.LOW,
                                    true,
                                    Clock.System.now()
                                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                                    null,
                                    null
                                )
                            )
                        }
                    }
                ),
                effect = null,
                onEvent = { },
                onNavigateToToDoDestination = {}
            )
        }
    }
}