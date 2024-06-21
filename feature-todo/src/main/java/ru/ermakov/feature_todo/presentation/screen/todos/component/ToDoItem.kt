package ru.ermakov.feature_todo.presentation.screen.todos.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
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
fun ToDoItem(
    toDo: ToDo,
    onItemClick: (toDoId: String) -> Unit,
    onDoneChange: (toDoId: String, isDone: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxHeight()
            .clickable { onItemClick(toDo.id) }
            .background(color = ToDoTheme.colors.backSecondary)
            .padding(end = ToDoTheme.size.medium)
    ) {
        ToDoCheckbox(
            isChecked = toDo.isDone,
            priority = toDo.priority,
            onCheckedChange = { isChecked -> onDoneChange(toDo.id, isChecked) }
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = ToDoTheme.size.small)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                PriorityImage(priority = toDo.priority)
                ToDoContentText(content = toDo.content, isDone = toDo.isDone)
            }
            if (toDo.deadline != null) {
                Text(
                    text = "${stringResource(id = R.string.deadline)}: ${toDo.deadline}",
                    style = ToDoTheme.typography.subhead,
                    color = ToDoTheme.colors.labelTertiary,
                    modifier = Modifier.padding(top = ToDoTheme.size.small)
                )
            }
        }
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = stringResource(id = R.string.to_do_information),
            tint = ToDoTheme.colors.labelTertiary
        )
    }
}

@Preview
@Composable
fun ToDoItemPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backSecondary) {
            ToDoItem(
                toDo = ToDo(
                    id = "1",
                    content = "Content content content content content content content Content content content content content content content Content content content ",
                    priority = Priority.LOW,
                    isDone = true,
                    creationDate = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                    modificationDate = null,
                    deadline = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()).date
                ),
                onItemClick = {},
                onDoneChange = { _, _ -> },
            )
        }
    }
}