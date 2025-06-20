package com.example.media.di

import com.example.media.domain.MediaPlayerManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MediaPlayerManagerEntryPoint {
    fun mediaPlayerManager(): MediaPlayerManager
}