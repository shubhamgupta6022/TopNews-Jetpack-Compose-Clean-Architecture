package com.sgupta.core.components.toolbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sgupta.core.theme.LightColors
import com.sgupta.core.components.toolbar.components.SearchBarContent
import com.sgupta.core.components.toolbar.components.TitleContent
import com.sgupta.core.components.toolbar.components.ToolbarActionButton
import com.sgupta.core.components.toolbar.model.ToolbarAction
import com.sgupta.core.components.toolbar.model.ToolbarContent
import com.sgupta.core.components.toolbar.utils.ToolbarDefaults

@Composable
fun GenericToolbar(
    modifier: Modifier = Modifier,
    navigationIcon: ToolbarAction? = null,
    content: ToolbarContent,
    actions: List<ToolbarAction> = emptyList(),
    backgroundColor: Color = Color.White,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    elevation: Dp = 0.dp,
    showSeparator: Boolean = true
) {
    Column(
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = backgroundColor,
            shadowElevation = elevation
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Navigation icon (back button, menu, etc.)
                navigationIcon?.let { navIcon ->
                    ToolbarActionButton(
                        action = navIcon,
                        contentColor = contentColor
                    )
                }

                // Content (Title, SearchBar, or Custom)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = if (navigationIcon != null) 8.dp else 16.dp)
                ) {
                    when (content) {
                        is ToolbarContent.Title -> {
                            TitleContent(
                                title = content.text,
                                style = content.style,
                                textAlign = content.textAlign,
                                color = contentColor
                            )
                        }
                        is ToolbarContent.SearchBar -> {
                            SearchBarContent(
                                placeholder = content.placeholder,
                                onSearch = content.onSearch,
                                focusRequester = content.focusRequester
                            )
                        }
                        is ToolbarContent.Custom -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                content = content.content
                            )
                        }
                    }
                }

                // Action buttons
                if (actions.isNotEmpty()) {
                    Row {
                        actions.forEach { action ->
                            ToolbarActionButton(
                                action = action,
                                contentColor = contentColor
                            )
                        }
                    }
                }
            }
        }

        if (showSeparator) {
            Separator()
        }
    }
}

@Composable
private fun Separator() {
    HorizontalDivider(
        color = LightColors.surface,
        thickness = 1.dp
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ToolbarComposablePreview() {
    GenericToolbar(
        navigationIcon = ToolbarDefaults.backButton { /* handle back */ },
        content = ToolbarContent.Title("Screen Title")
    )
} 