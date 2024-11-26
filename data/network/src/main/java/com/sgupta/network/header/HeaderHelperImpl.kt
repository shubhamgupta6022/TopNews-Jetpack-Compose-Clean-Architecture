package com.sgupta.network.header

import javax.inject.Inject

class HeaderHelperImpl @Inject constructor() : HeaderHelper {
    override fun getCommonHeaders(): HeaderMap {
        return HeaderMap().apply {
            addHeader("accept-encoding", "gzip")
            addHeader("connection", "Keep-Alive")
            addHeader("host", "newsapi.org")
            addHeader("user-agent", "okhttp/4.12.0")
        }
    }
}