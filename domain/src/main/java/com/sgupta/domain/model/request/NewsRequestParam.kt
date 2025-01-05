package com.sgupta.domain.model.request

data class NewsRequestParam(
    val sources: String,
    val page: Int,
    val pageSize: Int
)