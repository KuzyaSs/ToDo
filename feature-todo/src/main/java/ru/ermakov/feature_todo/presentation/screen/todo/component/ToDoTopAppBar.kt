package ru.ermakov.feature_todo.presentation.screen.todo.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoTopAppBar(
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    isSaveAvailable: Boolean,
    onCloseClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(color = ToDoTheme.colors.backPrimary, shadowElevation = ToDoTheme.size.extraSmall) {
        TopAppBar(
            title = {},
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close),
                    tint = ToDoTheme.colors.labelPrimary,
                    modifier = Modifier.clickable { onCloseClick() }
                )
            },
            actions = {
                Text(
                    text = stringResource(id = R.string.save).uppercase(),
                    style = if (isSaveAvailable) ToDoTheme.typography.button.copy(color = ToDoTheme.colors.blue)
                    else ToDoTheme.typography.button.copy(color = ToDoTheme.colors.labelDisable),
                    modifier = Modifier.clickable(enabled = isSaveAvailable) { onSaveClick() }
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = ToDoTheme.colors.backPrimary,
                scrolledContainerColor = ToDoTheme.colors.backPrimary
            ),
            scrollBehavior = topAppBarScrollBehavior,
            modifier = modifier.padding(horizontal = ToDoTheme.size.medium)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ToDoTopAppBarPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary) {
            ToDoTopAppBar(
                topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                isSaveAvailable = true,
                onCloseClick = {},
                onSaveClick = {},
            )
        }
    }
}