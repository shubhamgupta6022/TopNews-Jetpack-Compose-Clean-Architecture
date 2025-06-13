package com.sgupta.composite.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.sgupta.composite.splash.components.SplashContent
import com.sgupta.composite.splash.event.SplashScreenEvent
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val viewModel: SplashScreenViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()
    // Trigger navigation when the splash animation is finished
    var isAnimationFinished by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500) // Splash screen delay time (1.5 seconds)
        isAnimationFinished = true
        viewModel.onEvent(SplashScreenEvent.Loaded)
    }

    SplashContent(isAnimationFinished)
}