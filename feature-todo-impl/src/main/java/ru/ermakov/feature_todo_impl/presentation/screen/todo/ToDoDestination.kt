package ru.ermakov.feature_todo_impl.presentation.screen.todo

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
    composable<ToDoDestination> { navBackStackEntry ->
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