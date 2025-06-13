package com.sgupta.composite.newsdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sgupta.core.components.toolbar.GenericToolbar
import com.sgupta.core.components.toolbar.common.LoadingIndicator
import com.sgupta.core.components.toolbar.model.ToolbarContent
import com.sgupta.core.components.toolbar.utils.ToolbarDefaults
import com.sgupta.core.components.webview.GenericWebView
import com.sgupta.core.components.webview.WebViewCallbacks
import com.sgupta.navigation.Navigator

@Composable
fun NewsDetailScreen(
    title: String,
    url: String,
    navigator: Navigator
) {
    var isLoading by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        GenericToolbar(
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = ToolbarDefaults.backButton {
                navigator.goBack()
            },
            content = ToolbarContent.Title(title)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            GenericWebView(
                modifier = Modifier.fillMaxSize(),
                url = url,
                callbacks = object : WebViewCallbacks {
                    override fun onPageStarted(url: String) {
                        isLoading = true
                    }

                    override fun onPageFinished(url: String) {
                        isLoading = false
                    }

                    override fun onError(error: String) {
                        isLoading = false
                    }
                }
            )

            if (isLoading) {
                LoadingIndicator()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NewsDetailScreenPreview() {
    NewsDetailScreen("News Detail", "", navigator = Navigator())
}