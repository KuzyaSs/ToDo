package ru.ermakov.feature_todo_impl.presentation.screen.todos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo_api.domain.model.Priority
import ru.ermakov.feature_todo_api.domain.model.ToDo
import ru.ermakov.feature_todo_impl.R
import ru.ermakov.feature_todo_impl.presentation.screen.todos.component.ToDoList
import ru.ermakov.feature_todo_impl.presentation.screen.todos.component.ToDosTopAppBar

@Composable
fun ToDosScreen(
    state: ToDosState,
    effect: ToDosEffect?,
    onEvent: (ToDosEvent) -> Unit,
    onNavigateToToDoDestination: (toDoId: String?) -> Unit,
    onNavigateToSettingsDestination: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = effect) {
        when (effect) {
            is ToDosEffect.OnNavigateToToDoDestination -> onNavigateToToDoDestination(effect.toDoId)
            is ToDosEffect.OnNavigateToSettingsDestination -> onNavigateToSettingsDestination()
            null -> Unit
        }
    }
    Scaffold(
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
            ToDosTopAppBar(
                numOfDoneToDos = state.numOfDoneToDos,
                isOfflineMode = state.isOfflineMode,
                isDoneToDoVisible = state.isDoneToDoVisible,
                onDoneToDoVisibilityClick = { onEvent(ToDosEvent.OnDoneToDoVisibilityClick) },
                onSettingsClick = {onEvent(ToDosEvent.OnSettingsClick)},
                modifier = Modifier.padding(
                    start = ToDoTheme.size.large,
                    end = ToDoTheme.size.large,
                    bottom = ToDoTheme.size.medium
                ),
            )
            if (state.isLoading) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator(color = ToDoTheme.colors.blue)
                }
            } else {
                ToDoList(
                    undoneToDos = state.toDos.filter { toDo -> !toDo.isDone },
                    doneToDos = state.toDos.filter { toDo -> toDo.isDone },
                    isDoneToDoVisible = state.isDoneToDoVisible,
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
                    },
                    isLoading = false,
                ),
                effect = null,
                onEvent = { },
                onNavigateToToDoDestination = {},
                onNavigateToSettingsDestination = {},
            )
        }
    }
}