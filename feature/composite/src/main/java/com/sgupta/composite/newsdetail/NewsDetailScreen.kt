package com.sgupta.composite.newsdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sgupta.core.components.toolbar.GenericToolbar
import com.sgupta.core.components.toolbar.model.ToolbarContent
import com.sgupta.core.components.toolbar.utils.ToolbarDefaults
import com.sgupta.core.components.webview.GenericWebView
import com.sgupta.core.components.webview.WebViewCallbacks

@Composable
fun NewsDetailScreen(
    onBackClick: () -> Unit,
    title: String,
    url: String
) {
    Column(modifier = Modifier.fillMaxSize()) {
        GenericToolbar(
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = ToolbarDefaults.backButton {
                onBackClick()
            },
            content = ToolbarContent.Title(title)
        )
        
        GenericWebView(
            modifier = Modifier.fillMaxSize(),
            url = "",
            callbacks = object : WebViewCallbacks {
                override fun onPageStarted(url: String) {
                    // Handle page load start
                }

                override fun onPageFinished(url: String) {
                    // Handle page load finish
                }

                override fun onError(error: String) {
                    // Handle error
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NewsDetailScreenPreview() {
    NewsDetailScreen(onBackClick = {}, "News Detail", "")
}