package com.sgupta.composite.reels

// UI State
data class ReelsUiState(
    val currentVideoIndex: Int = 0,
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)