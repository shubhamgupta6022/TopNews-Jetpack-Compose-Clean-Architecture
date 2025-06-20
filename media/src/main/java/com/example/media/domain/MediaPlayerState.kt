package com.example.media.domain

sealed class MediaPlayerState {
    object Idle : MediaPlayerState()
    object Loading : MediaPlayerState()
    object Ready : MediaPlayerState()
    object Playing : MediaPlayerState()
    object Paused : MediaPlayerState()
    object Ended : MediaPlayerState()
    data class Error(val exception: Throwable) : MediaPlayerState()
}

data class MediaPlaybackInfo(
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val bufferedPercentage: Int = 0,
    val playbackSpeed: Float = 1.0f,
    val volume: Float = 1.0f
)