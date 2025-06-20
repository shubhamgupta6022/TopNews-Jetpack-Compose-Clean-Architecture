package com.example.media.utils

import androidx.media3.common.Player
import com.example.media.domain.CustomMediaItem

fun String.isHlsUrl(): Boolean = this.contains(".m3u8", ignoreCase = true)

fun String.isDashUrl(): Boolean = this.contains(".mpd", ignoreCase = true)

fun String.isStreamingUrl(): Boolean = isHlsUrl() || isDashUrl()

fun String.toCustomMediaItem(
    enableCaching: Boolean = true,
    headers: Map<String, String> = emptyMap()
): CustomMediaItem {
    return CustomMediaItem(
        id = this.hashCode().toString(),
        url = this,
        isHls = this.isHlsUrl(),
        enableCaching = enableCaching && !this.isStreamingUrl(),
        headers = headers
    )
}

fun Player.isCurrentlyPlaying(): Boolean = isPlaying && playWhenReady
