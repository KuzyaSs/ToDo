package ru.ermakov.core.di

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ermakov.core.network_manager.NetworkManagerImpl
import ru.ermakov.core.network_manager.NetworkManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Singleton
    @Provides
    fun provideNetworkManager(connectivityManager: ConnectivityManager): NetworkManager {
        return NetworkManagerImpl(connectivityManager = connectivityManager)
    }
}