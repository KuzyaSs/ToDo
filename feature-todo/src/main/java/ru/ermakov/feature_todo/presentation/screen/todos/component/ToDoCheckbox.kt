package ru.ermakov.feature_todo.presentation.screen.todos.component

import android.content.res.Configuration
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo.domain.model.Priority

@Composable
fun ToDoCheckbox(
    isChecked: Boolean,
    priority: Priority,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Checkbox(
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxDefaults.colors(
            checkedColor = ToDoTheme.colors.green,
            uncheckedColor = if (priority == Priority.URGENT) ToDoTheme.colors.red else ToDoTheme.colors.supportSeparator,
            checkmarkColor = ToDoTheme.colors.backSecondary
        ),
        modifier = modifier
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ToDoCheckboxPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary) {
            ToDoCheckbox(
                isChecked = true,
                priority = Priority.LOW,
                onCheckedChange = {}
            )
        }
    }
}