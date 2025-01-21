package com.sgupta.composite.splash.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgupta.composite.R
import com.sgupta.core.theme.Typography
import com.sgupta.core.theme.colorGrey500
import com.sgupta.core.theme.colorPrimaryWhite
import com.sgupta.core.theme.colorBgBlue

@Composable
fun SplashContent(isAnimationFinished: Boolean) {
    // Fade-out animation
    val alphaAnimation by animateFloatAsState(
        targetValue = if (isAnimationFinished) 0f else 1f,
        animationSpec = tween(durationMillis = 1000), // Fade-out animation duration
        label = "Alpha Animation"
    )

    // Scaling animation for the logo
    val scaleAnimation by animateFloatAsState(
        targetValue = if (isAnimationFinished) 1.5f else 1f,
        animationSpec = tween(durationMillis = 1000, easing = { it }), // Smooth scaling
        label = "Scale Animation"
    )

    // Text fade-in animation
    val textAlphaAnimation by animateFloatAsState(
        targetValue = if (isAnimationFinished) 0f else 1f,
        animationSpec = tween(durationMillis = 800, delayMillis = 200), // Text fade-in delay
        label = "Text Alpha Animation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorBgBlue)
            .graphicsLayer { alpha = alphaAnimation },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo with scaling animation
            Image(
                painter = painterResource(id = R.drawable.ic_top_news_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(144.dp)
                    .graphicsLayer(scaleX = scaleAnimation, scaleY = scaleAnimation)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Text with fade-in animation
            Text(
                "Top News",
                style = Typography.headlineMedium,
                color = colorPrimaryWhite,
                modifier = Modifier.graphicsLayer(alpha = textAlphaAnimation)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Static "Loading..." text
            Text("Loading...", style = Typography.titleSmall, color = colorGrey500)
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun PreviewSplashContent() {
    SplashContent(true)
}