package ru.ermakov.feature_settings_impl.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ermakov.feature_settings_impl.domain.model.Theme
import ru.ermakov.feature_settings_impl.domain.use_case.GetThemeUseCase
import ru.ermakov.feature_settings_impl.domain.use_case.SetThemeUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    private val _effect = Channel<SettingsEffect?>()
    val effect: Flow<SettingsEffect?> = _effect.receiveAsFlow()

    init {
        getTheme()
    }

    fun obtainEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnThemeMenuOpen -> showThemeMenu()
            is SettingsEvent.OnThemeMenuDismiss -> hideThemeMenu()
            is SettingsEvent.OnThemeChange -> changeTheme(theme = event.theme)
            is SettingsEvent.OnBackClick -> navigateBack()
        }
    }

    private fun getTheme() {
        viewModelScope.launch {
            getThemeUseCase().collect { theme ->
                _state.update { state -> state.copy(theme = theme) }
            }
        }
    }

    private fun showThemeMenu() {
        _state.update { state -> state.copy(isThemeMenuVisible = true) }
    }

    private fun hideThemeMenu() {
        _state.update { state -> state.copy(isThemeMenuVisible = false) }
    }

    private fun changeTheme(theme: Theme) {
        viewModelScope.launch {
            setThemeUseCase(theme)
            _state.update { state -> state.copy(isThemeMenuVisible = false) }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.send(SettingsEffect.OnNavigateBack)
        }
    }
}