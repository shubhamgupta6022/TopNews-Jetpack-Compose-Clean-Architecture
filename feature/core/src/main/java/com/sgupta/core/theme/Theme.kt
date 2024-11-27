package com.sgupta.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColors = lightColorScheme(
    primary = Color(0xFF001E6C),     // Dark Blue
    onPrimary = Color.White,         // Text on Dark Blue
    primaryContainer = Color(0xFF4A90E2), // Button Blue
    secondary = Color(0xFF87CEEB),   // Sky Blue
    onSecondary = Color.White,
    background = Color(0xFFF5F5F5),  // Soft Gray
    surface = Color(0xFFEBEDED),     // Light Gray (Card)
    onBackground = Color(0xFF333333), // Headline text
    onSurface = Color(0xFF666666),    // Subtitle text
    outline = Color(0xFFD6D6D6),     // Divider/Border
)

@Composable
fun NewsAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}