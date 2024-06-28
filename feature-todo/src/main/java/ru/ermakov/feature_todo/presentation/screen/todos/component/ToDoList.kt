package ru.ermakov.feature_todo.presentation.screen.todos.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun ToDoList(
    toDos: List<ToDo>,
    onToDoItemClick: (toDoId: String) -> Unit,
    onDoneChange: (toDoId: String, isDone: Boolean) -> Unit,
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
            items(toDos) { toDo ->
                ToDoItem(toDo = toDo, onItemClick = onToDoItemClick, onDoneChange = onDoneChange)
            }
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNewClick() }
                        .padding(horizontal = ToDoTheme.size.medium)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_to_do),
                        tint = ToDoTheme.colors.supportSeparator,
                        modifier = Modifier.padding(end = ToDoTheme.size.small)
                    )
                    Text(
                        text = stringResource(id = R.string.new_to_do),
                        style = ToDoTheme.typography.body.copy(color = ToDoTheme.colors.labelTertiary)
                    )
                }
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
                toDos = buildList {
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
                onToDoItemClick = {},
                onDoneChange = { _, _ -> },
                onNewClick = {},
                modifier = Modifier.padding(horizontal = ToDoTheme.size.medium)
            )
        }
    }
}