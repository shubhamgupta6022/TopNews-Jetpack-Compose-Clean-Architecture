package com.sgupta.domain.model

data class AIAssistantDataModel(
    val candidates: List<CandidateDataModel>,
    val modelVersion: String,
    val usageMetadata: UsageMetadataDataModel
)

data class UsageMetadataDataModel(
    val candidatesTokenCount: Int,
    val candidatesTokensDetails: List<CandidatesTokensDetailDataModel>,
    val promptTokenCount: Int,
    val promptTokensDetails: List<PromptTokensDetailDataModel>,
    val totalTokenCount: Int
)

data class PromptTokensDetailDataModel(
    val modality: String,
    val tokenCount: Int
)

data class CandidatesTokensDetailDataModel(
    val modality: String,
    val tokenCount: Int
)

data class CandidateDataModel(
    val avgLogprobs: Double,
    val citationMetadata: CitationMetadataDataModel,
    val content: ContentDataModel,
    val finishReason: String
)

data class ContentDataModel(
    val parts: List<PartDataModel>,
    val role: String
)

data class PartDataModel(
    val text: String
)

data class CitationMetadataDataModel(
    val citationSources: List<CitationSourceDataModel>
)

data class CitationSourceDataModel(
    val endIndex: Int,
    val startIndex: Int,
    val uri: String
)