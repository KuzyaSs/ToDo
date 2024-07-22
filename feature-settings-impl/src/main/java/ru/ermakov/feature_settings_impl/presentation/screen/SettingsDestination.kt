package ru.ermakov.feature_settings_impl.presentation.screen

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SettingsDestination

fun NavGraphBuilder.settingsDestination(onNavigateBack: () -> Unit) {
    composable<SettingsDestination>(
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    500, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(500, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    500, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(500, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        },
    ) {
        val settingsViewModel = hiltViewModel<SettingsViewModel>()
        val state by settingsViewModel.state.collectAsStateWithLifecycle()
        val effect by settingsViewModel.effect.collectAsStateWithLifecycle(null)
        SettingsScreen(
            state = state,
            effect = effect,
            onEvent = settingsViewModel::obtainEvent,
            onNavigateBack = onNavigateBack
        )
    }
}