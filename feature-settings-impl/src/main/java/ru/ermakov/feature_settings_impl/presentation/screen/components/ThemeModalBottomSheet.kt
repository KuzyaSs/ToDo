package ru.ermakov.feature_settings_impl.presentation.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_settings_impl.domain.model.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeModalBottomSheet(
    theme: Theme,
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
                ThemeItem(
                    theme = Theme.LIGHT,
                    isSelected = theme == Theme.LIGHT,
                    onItemClick = { theme -> onItemClick(theme) },
                )
                ThemeItem(
                    theme = Theme.DARK,
                    isSelected = theme == Theme.DARK,
                    onItemClick = { theme -> onItemClick(theme) },
                )
                ThemeItem(
                    theme = Theme.SYSTEM,
                    isSelected = theme == Theme.SYSTEM,
                    onItemClick = { theme -> onItemClick(theme) },
                )
            }
        }
    }
}
