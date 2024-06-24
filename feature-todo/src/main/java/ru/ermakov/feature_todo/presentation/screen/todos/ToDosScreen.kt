package ru.ermakov.feature_todo.presentation.screen.todos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import ru.ermakov.feature_todo.R
import ru.ermakov.feature_todo.domain.model.Priority
import ru.ermakov.feature_todo.domain.model.ToDo
import ru.ermakov.feature_todo.presentation.screen.todos.component.ToDoList

@Composable
fun ToDosScreen(
    state: ToDosState,
    effect: ToDosEffect?,
    onEvent: (ToDosEvent) -> Unit,
    onNavigateToToDoDestination: (toDoId: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = effect) {
        when (effect) {
            is ToDosEffect.OnNavigateToToDoDestination -> onNavigateToToDoDestination(effect.toDoId)
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
                    text = stringResource(id = R.string.num_of_done_to_do, state.numOfDoneToDos),
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
            ToDoList(
                toDos = if (state.isDoneToDoVisible) state.toDos else state.toDos.filter { toDo -> !toDo.isDone },
                onToDoItemClick = { toDoId ->
                    onEvent(ToDosEvent.OnToDoItemClick(toDoId = toDoId))
                },
                onDoneChange = { toDoId, isDone ->
                    onEvent(ToDosEvent.OnDoneChange(toDoId = toDoId, isDone = isDone))
                },
                onNewClick = { onEvent(ToDosEvent.OnNewClick) }
            )
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