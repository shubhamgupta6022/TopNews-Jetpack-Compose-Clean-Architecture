package com.example.media.impl

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.example.media.cache.MediaCacheManager
import com.example.media.domain.CustomMediaItem
import com.example.media.domain.MediaPlaybackInfo
import com.example.media.domain.MediaPlayerManager
import com.example.media.domain.MediaPlayerState
import com.example.media.utils.MediaPlayerConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@UnstableApi
class MediaPlayerManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cacheManager: MediaCacheManager
) : MediaPlayerManager {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    // State Management
    private val _playerState = MutableStateFlow<MediaPlayerState>(MediaPlayerState.Idle)
    override val playerState: StateFlow<MediaPlayerState> = _playerState.asStateFlow()
    
    private val _playbackInfo = MutableStateFlow(MediaPlaybackInfo())
    override val playbackInfo: StateFlow<MediaPlaybackInfo> = _playbackInfo.asStateFlow()
    
    private val _currentMediaItem = MutableStateFlow<CustomMediaItem?>(null)
    override val currentMediaItem: StateFlow<CustomMediaItem?> = _currentMediaItem.asStateFlow()
    
    // ExoPlayer Instance
    private var _exoPlayer: ExoPlayer? = null
    private val exoPlayer: ExoPlayer
        get() = _exoPlayer ?: createExoPlayer().also { 
            _exoPlayer = it
            setupPlayerListeners()
        }
    
    // Data Sources
    private val httpDataSourceFactory = DefaultHttpDataSource.Factory()
        .setAllowCrossProtocolRedirects(true)
        .setConnectTimeoutMs(MediaPlayerConstants.CONNECTION_TIMEOUT)
        .setReadTimeoutMs(MediaPlayerConstants.READ_TIMEOUT)
    
    private val dataSourceFactory = DefaultDataSource.Factory(context, httpDataSourceFactory)
    
    private val cacheDataSourceFactory = CacheDataSource.Factory()
        .setCache(cacheManager.getCache())
        .setUpstreamDataSourceFactory(dataSourceFactory)
        .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

    private fun createExoPlayer(): ExoPlayer {
        return ExoPlayer.Builder(context)
            .setSeekBackIncrementMs(MediaPlayerConstants.SEEK_BACK_INCREMENT)
            .setSeekForwardIncrementMs(MediaPlayerConstants.SEEK_FORWARD_INCREMENT)
            .build().apply {
                // Configure video scaling and rendering
                videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                
                // Configure audio attributes
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(C.USAGE_MEDIA)
                        .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                        .build(),
                    true
                )
                
                // Set default repeat mode
                repeatMode = Player.REPEAT_MODE_OFF
            }
    }
    
    private fun setupPlayerListeners() {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                _playerState.value = when (playbackState) {
                    Player.STATE_IDLE -> MediaPlayerState.Idle
                    Player.STATE_BUFFERING -> MediaPlayerState.Loading
                    Player.STATE_READY -> if (exoPlayer.playWhenReady) MediaPlayerState.Playing else MediaPlayerState.Ready
                    Player.STATE_ENDED -> MediaPlayerState.Ended
                    else -> MediaPlayerState.Idle
                }
                updatePlaybackInfo()
            }
            
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _playerState.value = if (isPlaying) MediaPlayerState.Playing else MediaPlayerState.Paused
                updatePlaybackInfo()
            }
            
            override fun onPlayerError(error: PlaybackException) {
                _playerState.value = MediaPlayerState.Error(error)
            }
        })
    }
    
    private fun updatePlaybackInfo() {
        _playbackInfo.value = _playbackInfo.value.copy(
            currentPosition = exoPlayer.currentPosition,
            duration = exoPlayer.duration.takeIf { it != C.TIME_UNSET } ?: 0L,
            bufferedPercentage = exoPlayer.bufferedPercentage,
            playbackSpeed = exoPlayer.playbackParameters.speed,
            volume = exoPlayer.volume
        )
    }
    
    override suspend fun playMedia(mediaItem: CustomMediaItem) {
        withContext(Dispatchers.Main) {
            try {
                _playerState.value = MediaPlayerState.Loading
                _currentMediaItem.value = mediaItem
                
                val mediaSource = createMediaSource(mediaItem)
                exoPlayer.apply {
                    setMediaSource(mediaSource)
                    prepare()
                    playWhenReady = true
                }
            } catch (e: Exception) {
                _playerState.value = MediaPlayerState.Error(e)
            }
        }
    }
    
    override suspend fun playMedia(url: String, enableCaching: Boolean) {
        val mediaItem = CustomMediaItem(
            id = url.hashCode().toString(),
            url = url,
            isHls = url.contains(".m3u8", ignoreCase = true),
            enableCaching = enableCaching
        )
        playMedia(mediaItem)
    }
    
    private fun createMediaSource(mediaItem: CustomMediaItem): MediaSource {
        val uri = android.net.Uri.parse(mediaItem.url)
        val mediaItemBuilder = MediaItem.Builder().setUri(uri)
        
        // Add headers if provided
        if (mediaItem.headers.isNotEmpty()) {
            httpDataSourceFactory.setDefaultRequestProperties(mediaItem.headers)
        }
        
        val factory = if (mediaItem.enableCaching) {
            cacheDataSourceFactory
        } else {
            dataSourceFactory
        }
        
        return when {
            mediaItem.isHls || mediaItem.url.contains(".m3u8", ignoreCase = true) -> {
                HlsMediaSource.Factory(factory).createMediaSource(mediaItemBuilder.build())
            }
            mediaItem.url.contains(".mpd", ignoreCase = true) -> {
                DashMediaSource.Factory(factory).createMediaSource(mediaItemBuilder.build())
            }
            else -> {
                ProgressiveMediaSource.Factory(factory).createMediaSource(mediaItemBuilder.build())
            }
        }
    }
    
    override fun play() {
        exoPlayer.play()
    }
    
    override fun pause() {
        exoPlayer.pause()
    }
    
    override fun stop() {
        exoPlayer.stop()
        _currentMediaItem.value = null
        _playerState.value = MediaPlayerState.Idle
    }
    
    override fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
    }
    
    override fun seekToNext() {
        exoPlayer.seekTo(exoPlayer.currentPosition + MediaPlayerConstants.SEEK_FORWARD_INCREMENT)
    }
    
    override fun seekToPrevious() {
        exoPlayer.seekTo(maxOf(0, exoPlayer.currentPosition - MediaPlayerConstants.SEEK_BACK_INCREMENT))
    }
    
    override fun setPlaybackSpeed(speed: Float) {
        exoPlayer.setPlaybackSpeed(speed)
    }
    
    override fun setVolume(volume: Float) {
        exoPlayer.volume = volume.coerceIn(0f, 1f)
    }
    
    override fun setRepeatMode(repeatMode: Int) {
        exoPlayer.repeatMode = repeatMode
    }
    
    override fun setShuffleMode(enabled: Boolean) {
        exoPlayer.shuffleModeEnabled = enabled
    }
    
    override fun getCurrentPosition(): Long = exoPlayer.currentPosition
    
    override fun getDuration(): Long = exoPlayer.duration.takeIf { it != C.TIME_UNSET } ?: 0L
    
    override fun getBufferedPercentage(): Int = exoPlayer.bufferedPercentage
    
    override fun isPlaying(): Boolean = exoPlayer.isPlaying
    
    override fun getPlayer(): ExoPlayer = exoPlayer
    
    override suspend fun preloadMedia(mediaItem: CustomMediaItem) {
        withContext(Dispatchers.IO) {
            cacheManager.preloadMedia(mediaItem.url)
        }
    }
    
    override suspend fun clearCache() {
        withContext(Dispatchers.IO) {
            cacheManager.clearCache()
        }
    }
    
    override fun getCacheSize(): Long = cacheManager.getCacheSize()
    
    override fun release() {
        _exoPlayer?.release()
        _exoPlayer = null
        _currentMediaItem.value = null
        _playerState.value = MediaPlayerState.Idle
        cacheManager.release()
    }
}