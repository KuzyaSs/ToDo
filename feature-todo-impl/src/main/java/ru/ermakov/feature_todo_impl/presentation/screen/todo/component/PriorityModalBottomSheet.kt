package ru.ermakov.feature_todo_impl.presentation.screen.todo.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo_api.domain.model.Priority
import ru.ermakov.feature_todo_impl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityModalBottomSheet(
    isMenuVisible: Boolean,
    onDismissRequest: () -> Unit,
    onItemClick: (priority: Priority) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (isMenuVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            containerColor = ToDoTheme.colors.backElevated,
            contentColor = ToDoTheme.colors.labelPrimary,
            modifier = modifier,
        ) {
            Column(modifier = Modifier.padding(bottom = ToDoTheme.size.small)) {
                Text(
                    text = stringResource(id = R.string.normal_priority_selection),
                    style = ToDoTheme.typography.body,
                    modifier = Modifier
                        .clickable { onItemClick(Priority.NORMAL) }
                        .padding(
                            vertical = ToDoTheme.size.medium,
                            horizontal = ToDoTheme.size.medium,
                        )
                        .fillMaxWidth(),
                )
                Text(
                    text = stringResource(id = R.string.low_priority_selection),
                    style = ToDoTheme.typography.body,
                    modifier = Modifier
                        .clickable { onItemClick(Priority.LOW) }
                        .padding(
                            vertical = ToDoTheme.size.medium,
                            horizontal = ToDoTheme.size.medium,
                        )
                        .fillMaxWidth(),
                )
                Text(
                    text = stringResource(id = R.string.urgent_priority_selection),
                    style = ToDoTheme.typography.body.copy(color = ToDoTheme.colors.red),
                    modifier = Modifier
                        .clickable { onItemClick(Priority.URGENT) }
                        .padding(
                            vertical = ToDoTheme.size.medium,
                            horizontal = ToDoTheme.size.medium,
                        )
                        .fillMaxWidth(),
                )
            }
        }
    }
}
