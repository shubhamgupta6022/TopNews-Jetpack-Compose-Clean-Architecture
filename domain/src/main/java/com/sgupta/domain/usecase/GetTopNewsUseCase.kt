package com.sgupta.domain.usecase

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
import javax.inject.Inject

class GetTopNewsUseCase @Inject constructor(
    private val newsRepo: TopNewsRepo
) : QueryUseCase<GetTopNewsUseCase.Param, Resource<NewsDataModel?>>() {
    override fun start(param: Param): Flow<Resource<NewsDataModel?>> = flow {
        val param = NewsRequestParam(param.q, param.page, param.pageSize)
        newsRepo.getTopNews(param)
            .onLoading {
                emit(Resource.Loading)
            }
            .onSuccess {
                emit(Resource.Success(it))
            }
            .onError {
                emit(Resource.Error(it))
            }
            .collect()
    }

    data class Param(val q: String, val page: Int, val pageSize: Int)
}