package com.sgupta.domain.model

data class NewsDataModel(
    val articles: List<ArticleDataModel>,
    val status: String,
    val totalResults: Int
)

data class ArticleDataModel(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceDataModel,
    val title: String,
    val url: String,
    val urlToImage: String
)

data class SourceDataModel(
    val id: String,
    val name: String
)