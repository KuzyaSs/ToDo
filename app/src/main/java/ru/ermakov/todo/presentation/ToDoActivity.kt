package ru.ermakov.todo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.ermakov.core.presentation.theme.ToDoTheme
import ru.ermakov.feature_settings_impl.presentation.screen.SettingsDestination
import ru.ermakov.feature_settings_impl.presentation.screen.settingsDestination
import ru.ermakov.feature_todo_impl.presentation.screen.todo.ToDoDestination
import ru.ermakov.feature_todo_impl.presentation.screen.todo.toDoDestination
import ru.ermakov.feature_todo_impl.presentation.screen.todos.ToDosDestination
import ru.ermakov.feature_todo_impl.presentation.screen.todos.toDosDestination

@AndroidEntryPoint
class ToDoActivity : ComponentActivity() {
    private val assetReader = AssetReader(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel = hiltViewModel<MainViewModel>()
            val state by mainViewModel.state.collectAsStateWithLifecycle()
            ToDoTheme(darkTheme = state.isDarkTheme ?: isSystemInDarkTheme()) {
                Surface(modifier = Modifier.fillMaxSize(), color = ToDoTheme.colors.backPrimary) {
                    ToDoAppScreen(navController = rememberNavController())
                }
            }
/*            val imageLoader = GlideDivImageLoader(this)
            val configuration = DivConfiguration.Builder(imageLoader).build()

            val divJson = assetReader.read("sample.json")
            val templatesJson = divJson.optJSONObject("templates")
            val cardJson = divJson.getJSONObject("card")


            val divContext = Div2Context(
                baseContext = this,
                configuration = configuration,
                lifecycleOwner = this
            )
            val divView = Div2ViewFactory(divContext, templatesJson).createView(cardJson)
            AndroidView(factory = { context -> divView })*/
        }
    }
}

@Composable
fun ToDoAppScreen(navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = ToDosDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        toDosDestination(
            onNavigateToToDoDestination = { toDoId ->
                navController.navigate(ToDoDestination(toDoId = toDoId))
            },
            onNavigateToSettingsDestination = { navController.navigate(SettingsDestination) },
        )
        toDoDestination(onNavigateBack = { navController.popBackStack() })
        settingsDestination(onNavigateBack = { navController.popBackStack() })
    }
}