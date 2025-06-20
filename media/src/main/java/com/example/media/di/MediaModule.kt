package com.example.media.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import com.example.media.cache.MediaCacheManager
import com.example.media.domain.MediaPlayerManager
import com.example.media.impl.MediaPlayerManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaModule {

    @OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun provideMediaCacheManager(
        @ApplicationContext context: Context
    ): MediaCacheManager = MediaCacheManager(context)

    @OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun provideMediaPlayerManager(
        @ApplicationContext context: Context,
        cacheManager: MediaCacheManager
    ): MediaPlayerManager = MediaPlayerManagerImpl(context, cacheManager)
}