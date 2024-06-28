package ru.ermakov.feature_todo.presentation.screen.todos.component

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ru.ermakov.core.presentation.theme.ToDoTheme

@Composable
fun ToDoContentText(content: String, isDone: Boolean) {
    Text(
        text = content,
        style = if (isDone) ToDoTheme.typography.body.copy(
            color = ToDoTheme.colors.labelTertiary,
            textDecoration = TextDecoration.LineThrough
        ) else ToDoTheme.typography.body,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ToDoContentTextPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary) {
            ToDoContentText(content = "Some content", isDone = true)
        }
    }
}