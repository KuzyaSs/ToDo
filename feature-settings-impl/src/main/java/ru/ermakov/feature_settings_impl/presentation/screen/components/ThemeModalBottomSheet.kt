package ru.ermakov.feature_settings_impl.presentation.screen.components

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
import ru.ermakov.feature_settings_impl.R
import ru.ermakov.feature_settings_impl.domain.model.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeModalBottomSheet(
    isMenuVisible: Boolean,
    onDismissRequest: () -> Unit,
    onItemClick: (theme: Theme) -> Unit,
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
                    text = stringResource(id = R.string.light_theme),
                    style = ToDoTheme.typography.body,
                    modifier = Modifier
                        .clickable { onItemClick(Theme.LIGHT) }
                        .padding(
                            vertical = ToDoTheme.size.medium,
                            horizontal = ToDoTheme.size.medium,
                        )
                        .fillMaxWidth(),
                )
                Text(
                    text = stringResource(id = R.string.dark_theme),
                    style = ToDoTheme.typography.body,
                    modifier = Modifier
                        .clickable { onItemClick(Theme.DARK) }
                        .padding(
                            vertical = ToDoTheme.size.medium,
                            horizontal = ToDoTheme.size.medium,
                        )
                        .fillMaxWidth(),
                )
                Text(
                    text = stringResource(id = R.string.system_theme),
                    style = ToDoTheme.typography.body,
                    modifier = Modifier
                        .clickable { onItemClick(Theme.SYSTEM) }
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
