package ru.ermakov.feature_todo_impl.presentation.screen.todo.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.datetime.LocalDate
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo_impl.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeadlineSelector(
    deadline: LocalDate?,
    onDeadlineSwitchChange: (isChecked: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = ToDoTheme.size.medium)
            .semantics(mergeDescendants = true) {
                toggleableState = if (deadline != null) ToggleableState.On else ToggleableState.Off
            }
            .toggleable(
                value = deadline != null,
                role = Role.Switch,
                onValueChange = { isChecked -> onDeadlineSwitchChange(isChecked) })
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.deadline),
                style = ToDoTheme.typography.body

            )
            if (deadline != null) {
                Text(
                    text = deadline.toString(),
                    style = ToDoTheme.typography.subhead.copy(color = ToDoTheme.colors.blue),
                    modifier = Modifier.padding(top = ToDoTheme.size.small)
                )
            }
        }
        Switch(
            checked = deadline != null,
            onCheckedChange = { isChecked -> onDeadlineSwitchChange(isChecked) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = ToDoTheme.colors.blue,
                checkedTrackColor = ToDoTheme.colors.blue.copy(alpha = 0.3f),
                checkedBorderColor = Color.Transparent,
                uncheckedThumbColor = ToDoTheme.colors.backElevated,
                uncheckedTrackColor = ToDoTheme.colors.supportOverlay,
                uncheckedBorderColor = Color.Transparent,
            ),
            modifier = Modifier.semantics { this.invisibleToUser() }
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DeadlineSelectorPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary) {
            DeadlineSelector(
                deadline = null,
                onDeadlineSwitchChange = {}
            )
        }
    }
}