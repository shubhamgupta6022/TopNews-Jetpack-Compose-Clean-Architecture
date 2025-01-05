package com.sgupta.composite.repoimpl

import com.sgupta.composite.api.NewsApiService
import com.sgupta.composite.model.toNewsDataModel
import com.sgupta.core.flows.toResponseFlow
import com.sgupta.core.network.Resource
import com.sgupta.domain.model.NewsDataModel
import com.sgupta.domain.model.request.NewsRequestParam
import com.sgupta.domain.repo.TopNewsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopNewsRepoImpl @Inject constructor(
    private val newsApiService: NewsApiService
) : TopNewsRepo {

    private val apiKey = "1dd86753d6294a93af5486a8f49fd81e"

    override fun getTopNews(param: NewsRequestParam): Flow<Resource<NewsDataModel>> {
        return toResponseFlow(
            apiCall = {
                newsApiService.getTopHeadlines(param.sources, param.page, param.pageSize, apiKey)
            }, mapper = {
                it?.toNewsDataModel()
            }
        )
    }
}