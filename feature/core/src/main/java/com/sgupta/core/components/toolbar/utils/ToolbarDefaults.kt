package com.sgupta.core.components.toolbar.utils

import androidx.compose.runtime.Composable
import com.sgupta.feature.core.R
import com.sgupta.core.components.toolbar.model.ToolbarAction

object ToolbarDefaults {
    @Composable
    fun backButton(onBackClick: () -> Unit) = ToolbarAction(
        icon = R.drawable.ic_arrow_back,
        contentDescription = "Back",
        onClick = onBackClick
    )

//    @Composable
//    fun menuButton(onMenuClick: () -> Unit) = ToolbarAction(
//        icon = R.drawable.ic_menu,
//        contentDescription = "Menu",
//        onClick = onMenuClick
//    )
//
//    @Composable
//    fun shareAction(onShareClick: () -> Unit) = ToolbarAction(
//        icon = R.drawable.ic_share,
//        contentDescription = "Share",
//        onClick = onShareClick
//    )
//
//    @Composable
//    fun bookmarkAction(
//        isBookmarked: Boolean,
//        onBookmarkClick: () -> Unit
//    ) = ToolbarAction(
//        icon = if (isBookmarked) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border,
//        contentDescription = if (isBookmarked) "Remove bookmark" else "Add bookmark",
//        onClick = onBookmarkClick,
//        tint = if (isBookmarked) MaterialTheme.colorScheme.primary else Color.Unspecified
//    )
} 