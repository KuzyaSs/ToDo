package ru.ermakov.feature_settings_impl.presentation.screen.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import ru.ermakov.feature_settings_impl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopAppBar(
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(color = ToDoTheme.colors.backPrimary, shadowElevation = ToDoTheme.size.extraSmall) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.settings),
                    style = ToDoTheme.typography.title,
                    modifier = modifier.padding(start = ToDoTheme.size.medium),
                )
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = stringResource(id = ru.ermakov.core.R.string.back),
                    tint = ToDoTheme.colors.labelPrimary,
                    modifier = Modifier.clickable { onBackClick() }
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = ToDoTheme.colors.backPrimary,
                scrolledContainerColor = ToDoTheme.colors.backPrimary
            ),
            scrollBehavior = topAppBarScrollBehavior,
            modifier = modifier.padding(horizontal = ToDoTheme.size.medium),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsTopAppBarPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary) {
            SettingsTopAppBar(
                topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                onBackClick = {},
            )
        }
    }
}
