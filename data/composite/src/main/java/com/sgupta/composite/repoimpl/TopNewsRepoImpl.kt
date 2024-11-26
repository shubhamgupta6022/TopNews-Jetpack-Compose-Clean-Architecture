package com.sgupta.composite.repoimpl

import com.sgupta.composite.api.NewsApiService
import com.sgupta.domain.TopNewsRepo
import javax.inject.Inject

class TopNewsRepoImpl @Inject constructor(
    private val newsApiService: NewsApiService
) : TopNewsRepo {

    private val apiKey = "1dd86753d6294a93af5486a8f49fd81e"

    override suspend fun getTopNews() {
        newsApiService.getTopHeadlines(apiKey)
    }
}