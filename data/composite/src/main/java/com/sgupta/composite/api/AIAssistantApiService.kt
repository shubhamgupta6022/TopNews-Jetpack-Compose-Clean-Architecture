package com.sgupta.composite.api

import com.sgupta.composite.model.AIAssistantResponseModel
import com.sgupta.composite.model.request.AIAssistantRequestBodyModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AIAssistantApiService {
    @Headers("Content-Type: application/json")
    @POST("/gemini-2.0-flash:generateContent")
    suspend fun generateContent(
        @Query("key") key: String,
        @Body requestBody: AIAssistantRequestBodyModel
    ): Response<AIAssistantResponseModel?>
}