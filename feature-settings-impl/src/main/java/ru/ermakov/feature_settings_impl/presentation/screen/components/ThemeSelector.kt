package ru.ermakov.feature_settings_impl.presentation.screen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_settings_impl.R
import ru.ermakov.feature_settings_impl.domain.model.Theme
import ru.ermakov.feature_todo_impl.presentation.utils.toStringTheme

@Composable
fun ThemeSelector(
    theme: Theme,
    isMenuVisible: Boolean,
    onMenuClick: () -> Unit,
    onDismissRequest: () -> Unit,
    onItemClick: (theme: Theme) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = ToDoTheme.size.medium)
            .clickable { onMenuClick() }
    ) {
        DropdownMenu(
            expanded = isMenuVisible,
            onDismissRequest = { onDismissRequest() },
            modifier = Modifier.background(color = ToDoTheme.colors.backElevated)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = R.string.light_theme),
                        style = ToDoTheme.typography.body,
                    )
                },
                onClick = { onItemClick(Theme.LIGHT) }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = R.string.dark_theme),
                        style = ToDoTheme.typography.body,
                    )
                },
                onClick = { onItemClick(Theme.DARK) }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = R.string.system_theme),
                        style = ToDoTheme.typography.body,
                    )
                },
                onClick = { onItemClick(Theme.SYSTEM) }
            )
        }
        Text(
            text = stringResource(id = R.string.theme),
            style = ToDoTheme.typography.body,
            modifier = Modifier.padding(bottom = ToDoTheme.size.small)
        )
        Text(
            text = theme.toStringTheme(),
            style = ToDoTheme.typography.subhead.copy(
                color = ToDoTheme.colors.labelTertiary
            ),
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ThemeSelectorPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary) {
            ThemeSelector(
                theme = Theme.LIGHT,
                isMenuVisible = true,
                onMenuClick = {},
                onDismissRequest = {},
                onItemClick = {},
            )
        }
    }
}