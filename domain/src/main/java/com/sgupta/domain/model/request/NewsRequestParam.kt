package com.sgupta.domain.model.request

data class NewsRequestParam(
    val q: String,
    val page: Int,
    val pageSize: Int
)