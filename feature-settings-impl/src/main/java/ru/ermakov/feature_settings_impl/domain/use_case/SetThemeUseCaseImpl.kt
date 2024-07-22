package ru.ermakov.feature_settings_impl.domain.use_case

import ru.ermakov.feature_settings_api.domain.repository.ThemeRepository
import ru.ermakov.feature_settings_impl.domain.model.Theme

class SetThemeUseCaseImpl(private val themeRepository: ThemeRepository) : SetThemeUseCase {
    override suspend fun invoke(theme: Theme) {
        themeRepository.setTheme(theme = theme)
    }
}