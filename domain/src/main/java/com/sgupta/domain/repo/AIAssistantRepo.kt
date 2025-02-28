package com.sgupta.domain.repo

import com.sgupta.core.network.Resource
import com.sgupta.domain.model.AIAssistantDataModel
import kotlinx.coroutines.flow.Flow

interface AIAssistantRepo {
    fun generateContent(prompt: String): Flow<Resource<AIAssistantDataModel>>
}