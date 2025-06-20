package com.example.media.domain

import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.StateFlow

interface MediaPlayerManager {
    val playerState: StateFlow<MediaPlayerState>
    val playbackInfo: StateFlow<MediaPlaybackInfo>
    val currentMediaItem: StateFlow<CustomMediaItem?>
    
    // Playback Control
    suspend fun playMedia(mediaItem: CustomMediaItem)
    suspend fun playMedia(url: String, enableCaching: Boolean = true)
    fun play()
    fun pause()
    fun stop()
    fun seekTo(position: Long)
    fun seekToNext()
    fun seekToPrevious()
    
    // Playback Settings
    fun setPlaybackSpeed(speed: Float)
    fun setVolume(volume: Float)
    fun setRepeatMode(repeatMode: Int)
    fun setShuffleMode(enabled: Boolean)
    
    // Player Information
    fun getCurrentPosition(): Long
    fun getDuration(): Long
    fun getBufferedPercentage(): Int
    fun isPlaying(): Boolean
    
    // Player Instance
    fun getPlayer(): ExoPlayer
    
    // Lifecycle
    fun release()
    
    // Cache Management
    suspend fun preloadMedia(mediaItem: CustomMediaItem)
    suspend fun clearCache()
    fun getCacheSize(): Long
}