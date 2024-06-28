package ru.ermakov.feature_todo.presentation.screen.todos.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo.R
import ru.ermakov.feature_todo.domain.model.Priority

@Composable
fun PriorityImage(priority: Priority, modifier: Modifier = Modifier) {
    when (priority) {
        Priority.LOW -> Image(
            painter = painterResource(id = R.drawable.low_priority),
            contentDescription = stringResource(id = R.string.low_priority),
            modifier = modifier.size(ToDoTheme.size.large)
        )

        Priority.NORMAL -> Unit

        Priority.URGENT -> Image(
            painter = painterResource(id = R.drawable.urgent_priority),
            contentDescription = stringResource(id = R.string.urgent_priority),
            modifier = modifier.size(ToDoTheme.size.large)
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PriorityImagePreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary) {
            PriorityImage(priority = Priority.LOW)
        }
    }
}