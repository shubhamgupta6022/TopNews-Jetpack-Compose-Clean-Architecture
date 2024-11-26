package com.sgupta.composite.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines/sources")
    suspend fun getTopHeadlines(@Query("apiKey") apiKey: String)

}