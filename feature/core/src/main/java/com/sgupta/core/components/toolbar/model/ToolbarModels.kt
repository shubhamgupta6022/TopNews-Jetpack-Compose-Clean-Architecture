package com.sgupta.core.components.toolbar.model

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sgupta.core.theme.typography.Typography

// Sealed class to define different toolbar content types
sealed class ToolbarContent {
    data class Title(
        val text: String,
        val style: TextStyle = Typography.displayLarge.copy(fontSize = 18.sp),
        val textAlign: TextAlign = TextAlign.Start
    ) : ToolbarContent()

    data class SearchBar(
        val placeholder: String = "Search...",
        val onSearch: (String) -> Unit,
        val focusRequester: FocusRequester? = null
    ) : ToolbarContent()

    data class Custom(
        val content: @Composable RowScope.() -> Unit
    ) : ToolbarContent()
}

// Data class for toolbar actions
data class ToolbarAction(
    val icon: Int,
    val contentDescription: String,
    val onClick: () -> Unit,
    val tint: Color = Color.Unspecified
) 