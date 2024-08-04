package ru.ermakov.feature_todo_impl.presentation.screen.todo.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo_api.domain.model.Priority
import ru.ermakov.feature_todo_impl.presentation.utils.toStringPriority

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PriorityItem(
    priority: Priority,
    isSelected: Boolean,
    onItemClick: (priority: Priority) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Text(
            text = priority.toStringPriority(),
            style = if (priority == Priority.URGENT) {
                ToDoTheme.typography.body.copy(color = ToDoTheme.colors.red)
            } else {
                ToDoTheme.typography.body
            },
            modifier = Modifier
                .clickable { onItemClick(priority) }
                .padding(
                    vertical = ToDoTheme.size.medium,
                    horizontal = ToDoTheme.size.medium,
                )
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .semantics { selected = isSelected },
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = ToDoTheme.colors.labelPrimary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = ToDoTheme.size.medium)
                    .semantics { this.invisibleToUser() },
            )
        }
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PriorityItemPreview() {
    ToDoTheme {
        PriorityItem(priority = Priority.URGENT, isSelected = true, onItemClick = {})
    }
}
