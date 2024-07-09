package ru.ermakov.feature_todo_impl.presentation.screen.todos.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
fun ToDosTopAppBar(
    numOfDoneToDos: Int,
    isOfflineMode: Boolean,
    isDoneToDoVisible: Boolean,
    onDoneToDoVisibilityClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.my_to_do),
                style = ToDoTheme.typography.largeTitle,
            )
            if (isOfflineMode) {
                Text(
                    text = stringResource(id = ru.ermakov.core.R.string.offline_mode),
                    style = ToDoTheme.typography.body.copy(color = ToDoTheme.colors.labelTertiary),
                    modifier = Modifier.padding(start = ToDoTheme.size.medium, top = ToDoTheme.size.extraSmall)
                )
            }
        }
        Spacer(modifier = Modifier.height(ToDoTheme.size.small))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.num_of_done_to_do, numOfDoneToDos),
                style = ToDoTheme.typography.body.copy(color = ToDoTheme.colors.labelTertiary),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (isDoneToDoVisible) Icons.Default.Visibility
                else Icons.Default.VisibilityOff,
                contentDescription = stringResource(id = R.string.change_done_to_do_visibility),
                tint = ToDoTheme.colors.blue,
                modifier = Modifier.clickable { onDoneToDoVisibilityClick() }
            )
        }
    }
}

@Preview
@Composable
fun ToDosTopAppBarPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary) {
            ToDosTopAppBar(
                numOfDoneToDos = 1,
                isOfflineMode = true,
                isDoneToDoVisible = true,
                onDoneToDoVisibilityClick = { },
                modifier = Modifier.padding(
                    start = ToDoTheme.size.large,
                    end = ToDoTheme.size.large,
                    bottom = ToDoTheme.size.medium
                )
            )
        }
    }
}