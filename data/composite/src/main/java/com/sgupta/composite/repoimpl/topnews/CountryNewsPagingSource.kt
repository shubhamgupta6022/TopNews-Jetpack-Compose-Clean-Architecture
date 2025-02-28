package com.sgupta.composite.repoimpl.topnews

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sgupta.composite.api.NewsApiService
import com.sgupta.composite.model.toNewsDataModel
import com.sgupta.core.flows.toPageSource
import com.sgupta.core.network.data
import com.sgupta.domain.model.NewsDataModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CountryNewsPagingSource @AssistedInject constructor(
    private val newsApiService: NewsApiService,
    @Assisted private val country: String,
    @Assisted("pageSize") private val pageSize: Int,
    @Assisted("apiKey") private val apiKey: String,
) : PagingSource<Int, NewsDataModel>() {

    @AssistedFactory
    interface CountryNewsPagingSourceFactory {
        fun createCountryNewsPagingSource(
            country: String,
            @Assisted("pageSize") pageSize: Int,
            @Assisted("apiKey") apiKey: String
        ): CountryNewsPagingSource
    }

    override fun getRefreshKey(state: PagingState<Int, NewsDataModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsDataModel> {
        val page = params.key ?: 1
        val result = toPageSource(
            apiCall = {
                newsApiService.getCountryNews(country, page, pageSize, apiKey)
            }, mapper = {
                it?.toNewsDataModel()
            }
        )
        val articles = result.data?.let {
            listOf(it)
        } ?: emptyList()
        return LoadResult.Page(
            data = articles,
            prevKey = if (page == 1) null else page - 1,
            nextKey = if (articles.isEmpty()) null else page + 1
        )
    }
}