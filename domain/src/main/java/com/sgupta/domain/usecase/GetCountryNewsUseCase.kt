package com.sgupta.domain.usecase

import androidx.paging.PagingData
import com.sgupta.core.usecase.QueryUseCase
import com.sgupta.domain.model.NewsDataModel
import com.sgupta.domain.model.request.NewsRequestParam
import com.sgupta.domain.repo.TopNewsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetCountryNewsUseCase @Inject constructor(
    private val newsRepo: TopNewsRepo
) : QueryUseCase<GetCountryNewsUseCase.Param, PagingData<NewsDataModel>>() {
    override fun start(param: Param): Flow<PagingData<NewsDataModel>> = flow {
        val param = NewsRequestParam(param.country, param.page, param.pageSize)
        newsRepo.getCountryNews(param)
            .onEach { pagingData ->
                emit(pagingData)
            }
            .collect()
    }

    data class Param(val country: String, val page: Int, val pageSize: Int)
}