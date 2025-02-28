package com.sgupta.composite.model

import com.sgupta.domain.model.AIAssistantDataModel
import com.sgupta.domain.model.CandidateDataModel
import com.sgupta.domain.model.CandidatesTokensDetailDataModel
import com.sgupta.domain.model.CitationMetadataDataModel
import com.sgupta.domain.model.CitationSourceDataModel
import com.sgupta.domain.model.ContentDataModel
import com.sgupta.domain.model.PartDataModel
import com.sgupta.domain.model.PromptTokensDetailDataModel
import com.sgupta.domain.model.UsageMetadataDataModel

data class AIAssistantResponseModel(
    val candidates: List<Candidate>,
    val modelVersion: String,
    val usageMetadata: UsageMetadata
)

data class UsageMetadata(
    val candidatesTokenCount: Int,
    val candidatesTokensDetails: List<CandidatesTokensDetail>,
    val promptTokenCount: Int,
    val promptTokensDetails: List<PromptTokensDetail>,
    val totalTokenCount: Int
)

data class PromptTokensDetail(
    val modality: String,
    val tokenCount: Int
)

data class CandidatesTokensDetail(
    val modality: String,
    val tokenCount: Int
)

data class Candidate(
    val avgLogprobs: Double,
    val citationMetadata: CitationMetadata,
    val content: Content,
    val finishReason: String
)

data class Content(
    val parts: List<Part>,
    val role: String
)

data class Part(
    val text: String
)

data class CitationMetadata(
    val citationSources: List<CitationSource>
)

data class CitationSource(
    val endIndex: Int,
    val startIndex: Int,
    val uri: String
)

fun AIAssistantResponseModel.toAiAssistantDataModel() = AIAssistantDataModel(
    candidates = this.candidates.map { it.toCandidateDataModel() },
    modelVersion = this.modelVersion,
    usageMetadata = this.usageMetadata.toUsageMetadataDataModel()
)

fun Candidate.toCandidateDataModel() = CandidateDataModel(
    avgLogprobs,
    citationMetadata = citationMetadata.toCitationMetadataDataModel(),
    content = content.toContentDataModel(),
    finishReason
)

fun CitationMetadata.toCitationMetadataDataModel() = CitationMetadataDataModel(
    citationSources = citationSources.map { it.toCitationSourceDataModel() }
)

fun CitationSource.toCitationSourceDataModel() = CitationSourceDataModel(
    endIndex, startIndex, uri
)

fun Content.toContentDataModel() = ContentDataModel(
    parts = parts.map { it.toPartDataModel() },
    role
)

fun Part.toPartDataModel() = PartDataModel(text)

fun UsageMetadata.toUsageMetadataDataModel() = UsageMetadataDataModel(
    candidatesTokenCount,
    candidatesTokensDetails = candidatesTokensDetails.map { it.toCandidatesTokensDetailDataModel() },
    promptTokenCount,
    promptTokensDetails = promptTokensDetails.map { it.toPromptTokensDetailDataModel() },
    totalTokenCount
)

fun CandidatesTokensDetail.toCandidatesTokensDetailDataModel() = CandidatesTokensDetailDataModel(
    modality, tokenCount
)

fun PromptTokensDetail.toPromptTokensDetailDataModel() = PromptTokensDetailDataModel(
    modality, tokenCount
)
