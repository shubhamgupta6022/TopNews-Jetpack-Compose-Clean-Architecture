package com.example.media.manager

import android.content.Context
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaPlayerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var _exoPlayer: ExoPlayer? = null
    private var currentVideoUrl: String? = null

    private val exoPlayer: ExoPlayer
        get() = _exoPlayer ?: createExoPlayer().also { _exoPlayer = it }

    @androidx.annotation.OptIn(UnstableApi::class)
    private fun createExoPlayer(): ExoPlayer {
        return ExoPlayer.Builder(context)
            .setSeekBackIncrementMs(10000)
            .setSeekForwardIncrementMs(10000)
            .build().apply {
                // Configure video scaling and rendering
                videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                // Set repeat mode for seamless looping
                repeatMode = Player.REPEAT_MODE_ONE
                // Configure audio attributes for media playback
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(C.USAGE_MEDIA)
                        .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                        .build(),
                    true
                )
            }
    }

    fun playVideo(videoUrl: String) {
        try {
            if (currentVideoUrl != videoUrl) {
                currentVideoUrl = videoUrl
                val mediaItem = MediaItem.Builder()
                    .setUri(videoUrl)
                    .build()

                exoPlayer.apply {
                    setMediaItem(mediaItem)
                    prepare()
                    playWhenReady = true
                }
            } else {
                exoPlayer.playWhenReady = true
            }
            Log.d("MediaPlayerManager", "Playing video: $exoPlayer")
        } catch (e: Exception) {
            // Handle video loading errors
            e.printStackTrace()
        }
    }

    fun playVideo() {
        exoPlayer.play()
    }

    fun pauseVideo() {
        exoPlayer.pause()
    }

    fun stopVideo() {
        exoPlayer.stop()
        currentVideoUrl = null
    }

    fun releasePlayer() {
        _exoPlayer?.release()
        _exoPlayer = null
        currentVideoUrl = null
    }

    fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
    }

    fun getCurrentPosition(): Long = exoPlayer.currentPosition

    fun getDuration(): Long = exoPlayer.duration

    fun isPlaying(): Boolean = exoPlayer.isPlaying

    fun getPlayer(): ExoPlayer = exoPlayer
}