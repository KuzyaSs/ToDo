package ru.ermakov.feature_todo.presentation.screen.todos

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object ToDosDestination

fun NavGraphBuilder.toDosDestination(onNavigateToToDoDestination: (toDoId: String?) -> Unit) {
    composable<ToDosDestination> {
        // Задание по лекциям Многопоточность и Корутины (для отображения в pull request).
        val toDosViewModel = hiltViewModel<ToDosViewModel>()
        val state by toDosViewModel.state.collectAsStateWithLifecycle()
        val effect by toDosViewModel.effect.collectAsStateWithLifecycle(null)
        ToDosScreen(
            state = state,
            effect = effect,
            onEvent = toDosViewModel::obtainEvent,
            onNavigateToToDoDestination = onNavigateToToDoDestination
        )
    }
}