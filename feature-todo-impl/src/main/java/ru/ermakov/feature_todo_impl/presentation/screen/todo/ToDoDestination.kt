package ru.ermakov.feature_todo_impl.presentation.screen.todo

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class ToDoDestination(
    val toDoId: String?
)

fun NavGraphBuilder.toDoDestination(onNavigateBack: () -> Unit) {
    composable<ToDoDestination>(
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
    ) { navBackStackEntry ->
        val toDoViewModel = hiltViewModel<ToDoViewModel>()
        val toDoDestination: ToDoDestination = navBackStackEntry.toRoute()
        var isFirstEnterScreen by rememberSaveable { mutableStateOf(true) }
        LaunchedEffect(isFirstEnterScreen) {
            if (isFirstEnterScreen && toDoDestination.toDoId != null) {
                toDoViewModel.obtainEvent(ToDoEvent.OnEnterScreen(toDoId = toDoDestination.toDoId))
                isFirstEnterScreen = false
            }
        }
        val state by toDoViewModel.state.collectAsStateWithLifecycle()
        val effect by toDoViewModel.effect.collectAsStateWithLifecycle(null)
        ToDoScreen(
            state = state,
            effect = effect,
            onEvent = toDoViewModel::obtainEvent,
            onNavigateBack = onNavigateBack
        )
    }
}