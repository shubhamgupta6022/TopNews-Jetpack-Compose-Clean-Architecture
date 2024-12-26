package com.sgupta.composite.model

import com.google.gson.annotations.SerializedName
import com.sgupta.domain.model.ArticleDataModel
import com.sgupta.domain.model.NewsDataModel
import com.sgupta.domain.model.SourceDataModel

data class NewsResponseModel(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<ArticleResponseModel>,
)

data class ArticleResponseModel(
    @SerializedName("source") val source: SourceResponseModel,
    @SerializedName("author") val author: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("urlToImage") val urlToImage: String? = null,
    @SerializedName("publishedAt") val publishedAt: String? = null,
    @SerializedName("content") val content: String? = null,
)

data class SourceResponseModel(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null
)

fun NewsResponseModel.toNewsDataModel() = NewsDataModel(
    status = status,
    totalResults = totalResults,
    articles = articles.map { it.toArticleDataModel() }
)

fun ArticleResponseModel.toArticleDataModel() = ArticleDataModel(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    source = source.toSourceDataModel(),
    title = title,
    url = url,
    urlToImage = urlToImage
)

fun SourceResponseModel.toSourceDataModel() = SourceDataModel(
    id = id,
    name = name
)