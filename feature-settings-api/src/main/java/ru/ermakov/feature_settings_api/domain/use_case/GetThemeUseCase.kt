package ru.ermakov.feature_settings_impl.domain.use_case

import kotlinx.coroutines.flow.Flow
import ru.ermakov.feature_settings_impl.domain.model.Theme

interface GetThemeUseCase: () -> Flow<Theme>