package ru.ermakov.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ermakov.network.BuildConfig
import ru.ermakov.network.data.interceptor.TokenInterceptor
import javax.inject.Singleton

private const val BASE_URL = "https://hive.mrdekk.ru/todo/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideToken(): String {
        return BuildConfig.TOKEN
    }

    @Singleton
    @Provides
    fun provideTokenInterceptor(token: String): TokenInterceptor {
        return TokenInterceptor(token = token)
    }

    @Singleton
    @Provides
    fun provideClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}