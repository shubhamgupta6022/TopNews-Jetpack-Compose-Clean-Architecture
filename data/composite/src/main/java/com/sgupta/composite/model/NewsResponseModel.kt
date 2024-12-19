package com.sgupta.composite.model

import com.google.gson.annotations.SerializedName
import com.sgupta.domain.model.ArticleDataModel
import com.sgupta.domain.model.NewsDataModel
import com.sgupta.domain.model.SourceDataModel

data class NewsResponseModel(
    @SerializedName("articles") val articles: List<ArticleResponseModel>,
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int
)

data class ArticleResponseModel(
    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String,
    @SerializedName("description") val description: String,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("source") val source: SourceResponseModel,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String
)

data class SourceResponseModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)

fun NewsResponseModel.toNewsDataModel() = NewsDataModel(
    status = status,
    totalResults = totalResults,
    articles = articles.map { it.toArticleDataModel() }
)

fun ArticleResponseModel.toArticleDataModel() = ArticleDataModel(
    author, content, description, publishedAt, source.toSourceDataModel(), title, url, urlToImage
)

fun SourceResponseModel.toSourceDataModel() = SourceDataModel(
    id, name
)