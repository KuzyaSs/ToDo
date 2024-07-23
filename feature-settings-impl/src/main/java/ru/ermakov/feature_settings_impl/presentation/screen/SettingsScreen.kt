package ru.ermakov.feature_settings_impl.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_settings_impl.presentation.screen.components.SettingsTopAppBar
import ru.ermakov.feature_settings_impl.presentation.screen.components.ThemeModalBottomSheet
import ru.ermakov.feature_settings_impl.presentation.screen.components.ThemeSelector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsState,
    effect: SettingsEffect?,
    onEvent: (SettingsEvent) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(effect) {
        when (effect) {
            SettingsEffect.OnNavigateBack -> onNavigateBack()
            null -> Unit
        }
    }

    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            SettingsTopAppBar(
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                onBackClick = { onEvent(SettingsEvent.OnBackClick) },
            )
        },
        containerColor = ToDoTheme.colors.backPrimary,
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ThemeModalBottomSheet(
                isMenuVisible = state.isThemeMenuVisible,
                onDismissRequest = { onEvent(SettingsEvent.OnThemeMenuDismiss) },
                onItemClick = { theme -> onEvent(SettingsEvent.OnThemeChange(theme = theme)) },
            )

            ThemeSelector(
                theme = state.theme,
                onMenuClick = { onEvent(SettingsEvent.OnThemeMenuOpen) },
                modifier.padding(
                    horizontal = ToDoTheme.size.medium,
                    vertical = ToDoTheme.size.small,
                ),
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenPreview() {
    ToDoTheme {
        Surface(color = ToDoTheme.colors.backPrimary, modifier = Modifier.fillMaxSize()) {
            SettingsScreen(
                state = SettingsState(),
                effect = null,
                onEvent = {},
                onNavigateBack = {},
            )
        }
    }
}