package com.sgupta.composite.aiassistant.states

import com.sgupta.composite.aiassistant.model.AIAssistantChatUiModel

data class AIAssistantBottomSheetViewState(
    val aiAssistantChatUiModel: List<AIAssistantChatUiModel>?,
    val loading: Boolean = false,
    val error: Throwable? = null,
)