package ru.ermakov.feature_todo_impl.presentation.screen.todo.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo_api.domain.model.Priority
import ru.ermakov.feature_todo_impl.R
import ru.ermakov.feature_todo_impl.presentation.utils.toStringPriority

@Composable
fun PrioritySelector(
    priority: Priority,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = ToDoTheme.size.medium)
            .clickable { onMenuClick() }
    ) {
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
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrioritySelectorPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary) {
            PrioritySelector(
                priority = Priority.NORMAL,
                onMenuClick = {},
            )
        }
    }
}