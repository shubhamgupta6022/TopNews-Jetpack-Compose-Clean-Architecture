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

class GetCountryNewsUseCase @Inject constructor(
    private val newsRepo: TopNewsRepo
) : QueryUseCase<GetCountryNewsUseCase.Param, Resource<NewsDataModel?>>() {
    override fun start(param: Param): Flow<Resource<NewsDataModel?>> = flow {
        val param = NewsRequestParam(param.country, param.page, param.pageSize)
        newsRepo.getCountryNews(param)
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

    data class Param(val country: String, val page: Int, val pageSize: Int)
}