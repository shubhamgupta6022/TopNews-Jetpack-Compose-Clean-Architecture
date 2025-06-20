package com.sgupta.composite.reels

import android.util.Log
import android.view.View
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    exoPlayer: ExoPlayer
) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                setKeepContentOnPlayerReset(true)
                useArtwork = false
                defaultArtwork = null

                // 👇 This will log if SurfaceView is available
                videoSurfaceView?.let { surfaceView ->
                    Log.d("SurfaceCheck", "SurfaceView found: $surfaceView")
                    surfaceView.visibility = View.VISIBLE
                } ?: Log.d("SurfaceCheck", "SurfaceView is NULL!")
            }
        },
        update = { playerView ->
            if (playerView.player != exoPlayer) {
                playerView.player = exoPlayer
            }

            // 👇 Force visibility and log
            playerView.videoSurfaceView?.let {
                Log.d("SurfaceCheck", "Rebinding surface view")
                it.visibility = View.VISIBLE
            } ?: Log.d("SurfaceCheck", "SurfaceView is NULL on update")
        },
        modifier = modifier
    )
}