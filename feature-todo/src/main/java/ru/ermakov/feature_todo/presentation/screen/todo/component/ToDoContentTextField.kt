package ru.ermakov.feature_todo.presentation.screen.todo.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo.R

@Composable
fun ToDoContentTextField(
    content: String,
    onValueChange: (content: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = ShapeDefaults.Small,
        colors = CardDefaults.cardColors(containerColor = ToDoTheme.colors.backSecondary),
        elevation = CardDefaults.cardElevation(defaultElevation = ToDoTheme.size.extraSmall),
    ) {
        OutlinedTextField(
            value = content,
            onValueChange = onValueChange,
            textStyle = ToDoTheme.typography.body,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.what_need_to_do),
                    style = ToDoTheme.typography.body.copy(color = ToDoTheme.colors.labelTertiary)
                )
            },
            minLines = 4,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = ToDoTheme.colors.backSecondary,
                unfocusedContainerColor = ToDoTheme.colors.backSecondary,
                cursorColor = ToDoTheme.colors.labelPrimary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent

            ),
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ToDoContentTextFieldPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary) {
            ToDoContentTextField(content = "Some content", onValueChange = {})
        }
    }
}