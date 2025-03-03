package com.sgupta.composite.repoimpl.aiassistant

import com.sgupta.composite.BuildConfig
import com.sgupta.composite.api.AIAssistantApiService
import com.sgupta.composite.model.request.AIAssistantRequestBodyModel
import com.sgupta.composite.model.request.Content
import com.sgupta.composite.model.request.Part
import com.sgupta.composite.model.toAiAssistantDataModel
import com.sgupta.core.flows.toResponseFlow
import com.sgupta.core.network.Resource
import com.sgupta.domain.model.AIAssistantDataModel
import com.sgupta.domain.repo.AIAssistantRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AIAssistantRepoImpl @Inject constructor(
    private val aiAssistantApiService: AIAssistantApiService
) : AIAssistantRepo {
    override fun generateContent(prompt: String): Flow<Resource<AIAssistantDataModel>> {
        val requestBody = AIAssistantRequestBodyModel(
            contents = listOf(
                Content(parts = listOf(Part(text = prompt)))
            )
        )
        return toResponseFlow(
            apiCall = {
                aiAssistantApiService.generateContent("gemini-2.0-flash",BuildConfig.GEMINI_API_KEY, requestBody)
            }, mapper = {
                it?.toAiAssistantDataModel()
            }
        )
    }
}