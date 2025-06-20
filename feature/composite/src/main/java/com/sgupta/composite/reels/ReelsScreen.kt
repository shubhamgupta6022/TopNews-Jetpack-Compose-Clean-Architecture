package com.sgupta.composite.reels

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.media.MediaPlayerManagerEntryPoint
import dagger.hilt.android.EntryPointAccessors

@Composable
fun ReelsScreen(
    viewModel: ReelsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val reelsLazyPagingItems = viewModel.reelsFlow.collectAsLazyPagingItems()

    val pagerState = rememberPagerState(pageCount = { reelsLazyPagingItems.itemCount })

    val context = LocalContext.current
    val mediaPlayerManager = remember {
        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            MediaPlayerManagerEntryPoint::class.java
        )
        entryPoint.mediaPlayerManager()
    }

    // Handle page changes
    LaunchedEffect(pagerState.currentPage) {
        if (reelsLazyPagingItems.itemCount > 0) {
            viewModel.onVideoChanged(pagerState.currentPage)
            reelsLazyPagingItems[pagerState.currentPage]?.let { video ->
                viewModel.playVideo(video, pagerState.currentPage)
            }
        }
    }

    // Dispose player when leaving screen
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayerManager.pauseVideo()
            mediaPlayerManager.releasePlayer()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            reelsLazyPagingItems[page]?.let { video ->
                ReelItem(
                    video = video,
                    exoPlayer = mediaPlayerManager.getPlayer(),
                    isCurrentVideo = page == uiState.currentVideoIndex,
                    onVideoClick = {
                        viewModel.togglePlayPause(video, page)
                    },
                    onLikeClick = {
                        viewModel.likeVideo(video.id)
                    },
                    onCommentClick = {
                        // Handle comment click
                    },
                    onShareClick = {
                        viewModel.shareVideo(video.id)
                    }
                )
            }
        }

        reelsLazyPagingItems.loadState.refresh.let { loadState ->
            when(loadState) {
                is LoadState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
                is LoadState.Error -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Failed to load reels",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { reelsLazyPagingItems.retry() }
                        ) {
                            Text("Retry")
                        }
                    }
                }
                else -> {}
            }
        }
    }
}
