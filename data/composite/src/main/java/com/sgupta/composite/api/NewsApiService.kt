package com.sgupta.composite.api

import com.sgupta.composite.model.NewsResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponseModel>

    @GET("top-headlines")
    suspend fun getCountryNews(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponseModel>

    @GET("top-headlines")
    suspend fun getCategoryNews(
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponseModel>

    @GET("everything")
    suspend fun getSearchNews(
        @Query("q") query: String,
        @Query("from") fromDate: String = "",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("apiKey") apiKey: String
    ): Response<NewsResponseModel>
}