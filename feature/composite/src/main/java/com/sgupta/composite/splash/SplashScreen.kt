package com.sgupta.composite.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sgupta.composite.splash.components.SplashContent
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    // Trigger navigation when the splash animation is finished
    var isAnimationFinished by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500) // Splash screen delay time (1.5 seconds)
        isAnimationFinished = true
        onSplashFinished()
    }

    SplashContent(isAnimationFinished)
}