package ru.ermakov.todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ermakov.feature_settings_impl.domain.model.Theme
import ru.ermakov.feature_settings_impl.domain.use_case.GetThemeUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getThemeUseCase: GetThemeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getThemeUseCase().collect { theme ->
                when (theme) {
                    Theme.LIGHT -> _state.update { state -> state.copy(isDarkTheme = false) }
                    Theme.DARK -> _state.update { state -> state.copy(isDarkTheme = true) }
                    Theme.SYSTEM -> _state.update { state -> state.copy(isDarkTheme = null) }
                }
            }
        }
    }
}