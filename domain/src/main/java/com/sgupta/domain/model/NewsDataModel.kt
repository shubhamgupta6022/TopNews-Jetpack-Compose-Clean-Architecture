package com.sgupta.domain.model

data class NewsDataModel(
    val articles: List<ArticleDataModel>,
    val status: String,
    val totalResults: Int
)

data class ArticleDataModel(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: SourceDataModel,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)

data class SourceDataModel(
    val id: String? = null,
    val name: String? = null
)