package com.sgupta.domain.usecase

import com.sgupta.core.network.Resource
import com.sgupta.core.usecase.QueryUseCase
import com.sgupta.domain.model.NewsDataModel
import com.sgupta.domain.model.request.NewsSearchQueryRequestParam
import com.sgupta.domain.repo.TopNewsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetNewsSearchQueryUseCase @Inject constructor(
    private val newsRepo: TopNewsRepo
) : QueryUseCase<GetNewsSearchQueryUseCase.Param, Resource<NewsDataModel>>() {
    override fun start(param: Param): Flow<Resource<NewsDataModel>> = flow {
        newsRepo.getNewsSearchQuery(NewsSearchQueryRequestParam(q = param.query))
            .onEach {
                emit(it)
            }
            .collect()
    }

    data class Param(val query: String)
}