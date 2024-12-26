package com.sgupta.composite.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.sgupta.composite.model.NewsResponseModel
import java.lang.reflect.Type

class NewsResponseDeserializer : JsonDeserializer<NewsResponseModel> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): NewsResponseModel {
        if (json.isJsonObject && !json.isJsonNull) {

        }
        return NewsResponseModel(articles = listOf(), status = "", totalResults = 1)
    }
}