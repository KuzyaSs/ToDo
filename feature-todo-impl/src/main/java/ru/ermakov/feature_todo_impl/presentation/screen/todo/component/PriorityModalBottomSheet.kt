package ru.ermakov.feature_todo_impl.presentation.screen.todo.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo_api.domain.model.Priority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityModalBottomSheet(
    priority: Priority,
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
                PriorityItem(
                    priority = Priority.NORMAL,
                    isSelected = priority == Priority.NORMAL,
                    onItemClick = { priority -> onItemClick(priority) },
                )
                PriorityItem(
                    priority = Priority.LOW,
                    isSelected = priority == Priority.LOW,
                    onItemClick = { priority -> onItemClick(priority) },
                )
                PriorityItem(
                    priority = Priority.URGENT,
                    isSelected = priority == Priority.URGENT,
                    onItemClick = { priority -> onItemClick(priority) },
                )
            }
        }
    }
}
