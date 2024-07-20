package ru.ermakov.feature_settings_impl.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ermakov.feature_settings_api.domain.repository.ThemeRepository
import ru.ermakov.feature_settings_impl.data.local.data_source.ThemeLocalDataSource
import ru.ermakov.feature_settings_impl.data.local.data_source.ThemeLocalDataSourceImpl
import ru.ermakov.feature_settings_impl.data.repository.ThemeRepositoryImpl
import ru.ermakov.feature_settings_impl.domain.use_case.GetThemeUseCase
import ru.ermakov.feature_settings_impl.domain.use_case.GetThemeUseCaseImpl
import ru.ermakov.feature_settings_impl.domain.use_case.SetThemeUseCase
import ru.ermakov.feature_settings_impl.domain.use_case.SetThemeUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    @Singleton
    @Provides
    fun provideThemeLocalDataSource(dataStore: DataStore<Preferences>): ThemeLocalDataSource {
        return ThemeLocalDataSourceImpl(dataStore = dataStore)
    }

    @Singleton
    @Provides
    fun provideThemeRepository(themeLocalDataSource: ThemeLocalDataSource): ThemeRepository {
        return ThemeRepositoryImpl(themeLocalDataSource = themeLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideGetThemeUseCase(themeRepository: ThemeRepository): GetThemeUseCase {
        return GetThemeUseCaseImpl(themeRepository = themeRepository)
    }

    @Singleton
    @Provides
    fun provideSetThemeUseCase(themeRepository: ThemeRepository): SetThemeUseCase {
        return SetThemeUseCaseImpl(themeRepository = themeRepository)
    }
}