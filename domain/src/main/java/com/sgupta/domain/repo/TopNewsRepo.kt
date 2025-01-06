package com.sgupta.domain.repo

import com.sgupta.core.network.Resource
import com.sgupta.domain.model.NewsDataModel
import com.sgupta.domain.model.request.NewsRequestParam
import kotlinx.coroutines.flow.Flow

interface TopNewsRepo {
    fun getTopNews(param: NewsRequestParam): Flow<Resource<NewsDataModel>>
    fun getCountryNews(param: NewsRequestParam): Flow<Resource<NewsDataModel>>
    fun getCategoryNews(param: NewsRequestParam): Flow<Resource<NewsDataModel>>
}