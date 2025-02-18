package com.sgupta.domain.usecase

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import com.sgupta.core.flows.onError
import com.sgupta.core.flows.onLoading
import com.sgupta.core.flows.onSuccess
import com.sgupta.core.network.Resource
import com.sgupta.core.usecase.QueryUseCase
import com.sgupta.domain.model.NewsDataModel
import com.sgupta.domain.model.request.NewsRequestParam
import com.sgupta.domain.repo.TopNewsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetCategoryNewsUseCase @Inject constructor(
    private val newsRepo: TopNewsRepo
) : QueryUseCase<GetCategoryNewsUseCase.Param, PagingData<NewsDataModel>>() {
    override fun start(param: Param): Flow<PagingData<NewsDataModel>> = flow {
        val param = NewsRequestParam(param.category, param.page, param.pageSize)
        newsRepo.getCategoryNews(param)
            .onEach { pagingData ->
                emit(pagingData)
            }
            .collect()
    }

    data class Param(val category: String, val page: Int, val pageSize: Int)
}