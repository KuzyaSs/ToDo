package ru.ermakov.feature_todo_impl.presentation.screen.todos.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun ToDoList(
    undoneToDos: List<ToDo>,
    doneToDos: List<ToDo>,
    isDoneToDoVisible: Boolean,
    onToDoItemClick: (toDoId: String) -> Unit,
    onDoneChange: (toDo: ToDo, isDone: Boolean) -> Unit,
    onNewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = ShapeDefaults.Small,
        colors = CardDefaults.cardColors(containerColor = ToDoTheme.colors.backSecondary),
        elevation = CardDefaults.cardElevation(defaultElevation = ToDoTheme.size.extraSmall),
        modifier = modifier.fillMaxWidth(),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = ToDoTheme.size.medium),
            verticalArrangement = Arrangement.spacedBy(ToDoTheme.size.medium)
        ) {
            if (undoneToDos.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(id = R.string.undone),
                        style = ToDoTheme.typography.body.copy(color = ToDoTheme.colors.labelTertiary),
                        modifier = Modifier.padding(horizontal = ToDoTheme.size.medium)
                    )
                }
                items(items = undoneToDos, key = { toDo -> toDo.id }) { toDo ->
                    ToDoItem(
                        toDo = toDo,
                        onItemClick = onToDoItemClick,
                        onDoneChange = onDoneChange,
                        modifier = Modifier.animateItem(),
                    )
                }
            }

            if (isDoneToDoVisible && doneToDos.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(id = R.string.done),
                        style = ToDoTheme.typography.body.copy(color = ToDoTheme.colors.labelTertiary),
                        modifier = Modifier.padding(horizontal = ToDoTheme.size.medium)
                    )
                }
                items(items = doneToDos, key = { toDo -> toDo.id }) { toDo ->
                    ToDoItem(
                        toDo = toDo,
                        onItemClick = onToDoItemClick,
                        onDoneChange = onDoneChange,
                        modifier = Modifier.animateItem(),
                    )
                }
            }

            item {
                NewToDoItem(onItemClick = { onNewClick() })
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ToDoListPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary) {
            ToDoList(
                doneToDos = buildList {
                    repeat(2) {
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
                                true,
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
                undoneToDos = buildList {
                    repeat(2) {
                        add(
                            ToDo(
                                "1",
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
                                false,
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
                isDoneToDoVisible = true,
                onToDoItemClick = {},
                onDoneChange = { _, _ -> },
                onNewClick = {},
                modifier = Modifier.padding(horizontal = ToDoTheme.size.medium)
            )
        }
    }
}