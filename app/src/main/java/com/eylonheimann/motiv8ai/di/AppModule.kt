package com.eylonheimann.motiv8ai.di

import android.content.Context
import com.eylonheimann.motiv8ai.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRepository(
        @ApplicationContext context: Context,
        webSocketClient: ItemsWebSocketClient
    ): Repository {
        return RepositoryImpl(webSocketClient)
    }

    @Singleton
    @Provides
    fun provideItemsWebSocketClient(): ItemsWebSocketClient {
        return WebSocketClientImpl()
    }
}