package ru.ermakov.feature_todo.presentation.screen.todo

import androidx.compose.runtime.getValue
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
    composable<ToDoDestination> { navBackStackEntry ->
        val toDoViewModel = hiltViewModel<ToDoViewModel>()
        val state by toDoViewModel.state.collectAsStateWithLifecycle()
        val effect by toDoViewModel.effect.collectAsStateWithLifecycle(null)
        val toDoDestination: ToDoDestination = navBackStackEntry.toRoute()
        ToDoScreen(
            state = state.copy(toDoId = toDoDestination.toDoId),
            effect = effect,
            onEvent = toDoViewModel::obtainEvent,
            onNavigateBack = onNavigateBack
        )
    }
}