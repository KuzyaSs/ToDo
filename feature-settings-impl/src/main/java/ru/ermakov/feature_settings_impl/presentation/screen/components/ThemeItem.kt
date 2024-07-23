package ru.ermakov.feature_settings_impl.presentation.screen.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_settings_impl.domain.model.Theme
import ru.ermakov.feature_todo_impl.presentation.utils.toStringTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ThemeItem(
    theme: Theme,
    isSelected: Boolean,
    onItemClick: (theme: Theme) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Text(
            text = theme.toStringTheme(),
            style = ToDoTheme.typography.body,
            modifier = Modifier
                .clickable { onItemClick(theme) }
                .padding(
                    vertical = ToDoTheme.size.medium,
                    horizontal = ToDoTheme.size.medium,
                )
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .semantics { selected = isSelected },
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = ToDoTheme.colors.labelPrimary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = ToDoTheme.size.medium)
                    .semantics { this.invisibleToUser() },
            )
        }
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PriorityItemPreview() {
    ToDoTheme {
        ThemeItem(theme = Theme.SYSTEM, isSelected = true, onItemClick = {})
    }
}
