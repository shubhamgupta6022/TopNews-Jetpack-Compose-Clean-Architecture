package com.sgupta.composite.reels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.media.manager.MediaPlayerManager
import com.sgupta.domain.model.ReelVideo
import com.sgupta.domain.usecase.GetReelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReelsViewModel @Inject constructor(
    private val getReelsUseCase: GetReelsUseCase,
    private val mediaPlayerManager: MediaPlayerManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReelsUiState())
    val uiState: StateFlow<ReelsUiState> = _uiState.asStateFlow()

    val reelsFlow: Flow<PagingData<ReelVideo>> = getReelsUseCase()

    private var currentVideoId: String? = null

    fun playVideo(video: ReelVideo, index: Int) {
        if (currentVideoId != video.id) {
            currentVideoId = video.id
            mediaPlayerManager.playVideo(video.videoUrl)
            _uiState.update { it.copy(currentVideoIndex = index, isPlaying = true) }
        } else if (!mediaPlayerManager.isPlaying()) {
            mediaPlayerManager.playVideo()
            _uiState.update { it.copy(isPlaying = true) }
        }
    }

    fun pauseVideo() {
        mediaPlayerManager.pauseVideo()
        _uiState.update { it.copy(isPlaying = false) }
    }

    fun togglePlayPause(video: ReelVideo, index: Int) {
        if (mediaPlayerManager.isPlaying()) {
            pauseVideo()
        } else {
            playVideo(video, index)
        }
    }

    fun onVideoChanged(index: Int) {
        _uiState.update { it.copy(currentVideoIndex = index) }
    }

    fun likeVideo(videoId: String) {
        viewModelScope.launch {
//            likeVideoUseCase(videoId)
        }
    }

    fun shareVideo(videoId: String) {
        viewModelScope.launch {
//            shareVideoUseCase(videoId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayerManager.releasePlayer()
    }
}