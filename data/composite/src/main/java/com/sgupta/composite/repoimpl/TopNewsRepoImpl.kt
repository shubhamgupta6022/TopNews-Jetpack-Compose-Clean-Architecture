package com.sgupta.composite.repoimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
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
    private val newsApiService: NewsApiService,
    private val categoryNewsPagingSourceFactory: CategoryNewsPagingSource.CategoryNewsPagingSourceFactory,
    private val countryNewsPagingSourceFactory: CountryNewsPagingSource.CountryNewsPagingSourceFactory
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

    override fun getCountryNews(param: NewsRequestParam): Flow<PagingData<NewsDataModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = param.pageSize,
                enablePlaceholders = false,
                initialLoadSize = param.pageSize
            ),
            pagingSourceFactory = {
                countryNewsPagingSourceFactory.createCountryNewsPagingSource(param.sources, param.pageSize, apiKey)
            }
        ).flow
    }

    override fun getCategoryNews(param: NewsRequestParam): Flow<PagingData<NewsDataModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = param.pageSize,
                enablePlaceholders = false,
                initialLoadSize = param.pageSize
            ),
            pagingSourceFactory = {
                categoryNewsPagingSourceFactory.createCategoryNewsPagingSource(param.sources, param.pageSize, apiKey)
            }
        ).flow
    }
}