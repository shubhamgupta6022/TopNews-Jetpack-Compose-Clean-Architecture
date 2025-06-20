package com.example.media

import com.example.media.manager.MediaPlayerManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MediaPlayerManagerEntryPoint {
    fun mediaPlayerManager(): MediaPlayerManager
}