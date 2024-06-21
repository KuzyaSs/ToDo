package ru.ermakov.todo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_todo.presentation.screen.todo.ToDoDestination
import ru.ermakov.feature_todo.presentation.screen.todo.toDoDestination
import ru.ermakov.feature_todo.presentation.screen.todos.ToDosDestination
import ru.ermakov.feature_todo.presentation.screen.todos.toDosDestination

@AndroidEntryPoint
class ToDoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = ToDoTheme.colors.backPrimary) {
                    ToDoAppScreen(navController = rememberNavController())
                }
            }
        }
    }
}

@Composable
fun ToDoAppScreen(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ToDosDestination) {
        toDosDestination(onNavigateToToDoDestination = { toDoId ->
            navController.navigate(ToDoDestination(toDoId = toDoId))
        })
        toDoDestination(onNavigateBack = { navController.popBackStack() })
    }
}