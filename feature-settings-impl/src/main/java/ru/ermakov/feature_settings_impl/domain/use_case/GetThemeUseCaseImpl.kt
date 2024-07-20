package ru.ermakov.feature_settings_impl.domain.use_case

import kotlinx.coroutines.flow.Flow
import ru.ermakov.feature_settings_api.domain.repository.ThemeRepository
import ru.ermakov.feature_settings_impl.domain.model.Theme

class GetThemeUseCaseImpl(private val themeRepository: ThemeRepository) : GetThemeUseCase {
    override fun invoke(): Flow<Theme> {
        return themeRepository.getTheme()
    }
}