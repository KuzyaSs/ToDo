package ru.ermakov.feature_settings_impl.domain.use_case

import ru.ermakov.feature_settings_impl.domain.model.Theme

interface SetThemeUseCase: suspend (Theme) -> Unit