package com.sgupta.composite.bottombarnavigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.sgupta.core.navigation.NewsDestination

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: NewsDestination
)