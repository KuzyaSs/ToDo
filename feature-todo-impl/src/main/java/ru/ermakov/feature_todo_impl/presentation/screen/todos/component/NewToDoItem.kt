package ru.ermakov.feature_todo_impl.presentation.screen.todos.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo_impl.R

@Composable
fun NewToDoItem(onItemClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NewToDoItemPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backSecondary) {
            NewToDoItem(onItemClick = { })
        }
    }
}