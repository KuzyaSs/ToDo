package ru.ermakov.feature_todo_impl.presentation.screen.todos

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
object ToDosDestination

fun NavGraphBuilder.toDosDestination(
    onNavigateToToDoDestination: (toDoId: String?) -> Unit,
    onNavigateToSettingsDestination: () -> Unit,
) {
    composable<ToDosDestination>(
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
        val toDosViewModel = hiltViewModel<ToDosViewModel>()
        val state by toDosViewModel.state.collectAsStateWithLifecycle()
        val effect by toDosViewModel.effect.collectAsStateWithLifecycle(null)
        ToDosScreen(
            state = state,
            effect = effect,
            onEvent = toDosViewModel::obtainEvent,
            onNavigateToToDoDestination = onNavigateToToDoDestination,
            onNavigateToSettingsDestination = onNavigateToSettingsDestination,
        )
    }
}