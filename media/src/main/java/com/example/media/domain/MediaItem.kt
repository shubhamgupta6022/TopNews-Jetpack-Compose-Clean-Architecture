package com.example.media.domain

import androidx.media3.common.MediaItem as ExoMediaItem

data class CustomMediaItem(
    val id: String,
    val url: String,
    val title: String? = null,
    val description: String? = null,
    val thumbnailUrl: String? = null,
    val isHls: Boolean = false,
    val headers: Map<String, String> = emptyMap(),
    val enableCaching: Boolean = true
) {
    fun toExoMediaItem(): ExoMediaItem {
        return ExoMediaItem.Builder()
            .setUri(url)
            .setMediaId(id)
            .apply {
                title?.let { setTag(it) }
            }
            .build()
    }
}
