package com.sgupta.domain.usecase

import com.sgupta.core.flows.onError
import com.sgupta.core.flows.onLoading
import com.sgupta.core.flows.onSuccess
import com.sgupta.core.network.Resource
import com.sgupta.core.usecase.QueryUseCase
import com.sgupta.domain.model.AIAssistantDataModel
import com.sgupta.domain.repo.AIAssistantRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenerateAIAssistantContentUseCase @Inject constructor(
    private val repo: AIAssistantRepo
):
    QueryUseCase<GenerateAIAssistantContentUseCase.Param, Resource<AIAssistantDataModel?>>() {

    override fun start(param: Param): Flow<Resource<AIAssistantDataModel?>> = flow {
        repo.generateContent(param.prompt)
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

    data class Param(
        val prompt: String
    )
}