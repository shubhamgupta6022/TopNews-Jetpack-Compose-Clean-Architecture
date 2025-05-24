package com.sgupta.core.components.webview

interface WebViewCallbacks {
    fun onPageStarted(url: String)
    fun onPageFinished(url: String)
    fun onError(error: String)
}