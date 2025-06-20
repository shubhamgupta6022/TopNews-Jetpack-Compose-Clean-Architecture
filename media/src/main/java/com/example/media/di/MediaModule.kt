package com.example.media.di

import android.content.Context
import com.example.media.manager.MediaPlayerManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaModule {

    @Provides
    @Singleton
    fun provideMediaPlayerManager(
        @ApplicationContext context: Context
    ): MediaPlayerManager = MediaPlayerManager(context)
}