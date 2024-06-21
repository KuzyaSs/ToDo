package ru.ermakov.feature_todo.presentation.screen.todo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo.R
import ru.ermakov.feature_todo.domain.model.Priority
import ru.ermakov.feature_todo.presentation.utils.toStringPriority

@Composable
fun PrioritySelector(
    priority: Priority,
    isMenuVisible: Boolean,
    onMenuClick: () -> Unit,
    onDismissRequest: () -> Unit,
    onItemClick: (priority: Priority) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = ToDoTheme.size.medium)
            .clickable { onMenuClick() }
    ) {
        DropdownMenu(
            expanded = isMenuVisible,
            onDismissRequest = { onDismissRequest() },
            modifier = Modifier.background(color = ToDoTheme.colors.backElevated)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = R.string.normal_priority_selection),
                        style = ToDoTheme.typography.body
                    )
                },
                onClick = { onItemClick(Priority.NORMAL) }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = R.string.low_priority_selection),
                        style = ToDoTheme.typography.body
                    )
                },
                onClick = { onItemClick(Priority.LOW) }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = R.string.urgent_priority_selection),
                        style = ToDoTheme.typography.body.copy(color = ToDoTheme.colors.red)
                    )
                },
                onClick = { onItemClick(Priority.URGENT) }
            )
        }
        Text(
            text = stringResource(id = R.string.priority),
            style = ToDoTheme.typography.body,
            modifier = Modifier.padding(bottom = ToDoTheme.size.small)
        )
        Text(
            text = priority.toStringPriority(),
            style = ToDoTheme.typography.subhead.copy(
                color = if (priority == Priority.URGENT) ToDoTheme.colors.red
                else ToDoTheme.colors.labelTertiary
            )
        )
    }
}

@Preview
@Composable
fun PrioritySelectorPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary) {
            PrioritySelector(
                priority = Priority.NORMAL,
                isMenuVisible = true,
                onMenuClick = {},
                onDismissRequest = {},
                onItemClick = {}
            )
        }
    }
}