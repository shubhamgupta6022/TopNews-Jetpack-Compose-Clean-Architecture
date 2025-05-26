package com.sgupta.analytics.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.sgupta.analytics.builder.AnalyticsEventBuilder
import com.sgupta.analytics.constants.AnalyticsEvents
import com.sgupta.analytics.constants.AnalyticsProperties
import com.sgupta.analytics.manager.AnalyticsManager
import com.sgupta.analytics.model.EventType

/**
 * Extension functions for easier analytics logging in Compose screens
 */

/**
 * Log screen view when the composable is first composed
 */
@Composable
fun AnalyticsManager.TrackScreenView(
    screenName: String,
    additionalProperties: Map<String, Any> = emptyMap()
) {
    val startTime = remember { System.currentTimeMillis() }
    
    LaunchedEffect(screenName) {
        logEvent(
            AnalyticsEventBuilder()
                .setScreenName(screenName)
                .setEventType(EventType.SCREEN_VIEW)
                .setEventName(AnalyticsEvents.SCREEN_VIEW)
                .addParameters(additionalProperties)
        )
    }
    
    DisposableEffect(screenName) {
        onDispose {
            val duration = System.currentTimeMillis() - startTime
            logEvent(
                AnalyticsEventBuilder()
                    .setScreenName(screenName)
                    .setEventType(EventType.CUSTOM)
                    .setEventName(AnalyticsEvents.SCREEN_LOAD_TIME)
                    .addParameter(AnalyticsProperties.LOAD_TIME_MS, duration)
            )
        }
    }
}

/**
 * Log button click with consistent parameters
 */
fun AnalyticsManager.logButtonClick(
    screenName: String,
    buttonName: String,
    buttonType: String = "default",
    additionalProperties: Map<String, Any> = emptyMap()
) {
    logEvent(
        AnalyticsEventBuilder()
            .setScreenName(screenName)
            .setEventType(EventType.CLICK)
            .setEventName(AnalyticsEvents.BUTTON_CLICKED)
            .addParameter(AnalyticsProperties.BUTTON_NAME, buttonName)
            .addParameter(AnalyticsProperties.BUTTON_TYPE, buttonType)
            .addParameters(additionalProperties)
    )
}

/**
 * Log news article click with news-specific parameters
 */
fun AnalyticsManager.logNewsArticleClick(
    screenName: String,
    title: String,
    url: String,
    source: String? = null,
    category: String? = null,
    position: Int? = null
) {
    val params = mutableMapOf<String, Any>(
        AnalyticsProperties.NEWS_TITLE to title,
        AnalyticsProperties.NEWS_URL to url
    )
    
    source?.let { params[AnalyticsProperties.NEWS_SOURCE] = it }
    category?.let { params[AnalyticsProperties.NEWS_CATEGORY] = it }
    position?.let { params[AnalyticsProperties.LIST_POSITION] = it }
    
    logEvent(
        AnalyticsEventBuilder()
            .setScreenName(screenName)
            .setEventType(EventType.CLICK)
            .setEventName(AnalyticsEvents.NEWS_ARTICLE_CLICKED)
            .addParameters(params)
    )
}

/**
 * Log search query with search-specific parameters
 */
fun AnalyticsManager.logSearchQuery(
    screenName: String,
    query: String,
    resultsCount: Int? = null
) {
    val params = mutableMapOf<String, Any>(
        AnalyticsProperties.SEARCH_QUERY to query
    )
    
    resultsCount?.let { params[AnalyticsProperties.SEARCH_RESULTS_COUNT] = it }
    
    logEvent(
        AnalyticsEventBuilder()
            .setScreenName(screenName)
            .setEventType(EventType.SEARCH)
            .setEventName(AnalyticsEvents.SEARCH_PERFORMED)
            .addParameters(params)
    )
}

/**
 * Log AI assistant interaction
 */
fun AnalyticsManager.logAIAssistantMessage(
    screenName: String,
    messageLength: Int,
    conversationTurn: Int
) {
    logEvent(
        AnalyticsEventBuilder()
            .setScreenName(screenName)
            .setEventType(EventType.CUSTOM)
            .setEventName(AnalyticsEvents.AI_MESSAGE_SENT)
            .addParameter(AnalyticsProperties.AI_MESSAGE_LENGTH, messageLength)
            .addParameter(AnalyticsProperties.AI_CONVERSATION_TURN, conversationTurn)
    )
}

/**
 * Log error events with error context
 */
fun AnalyticsManager.logError(
    screenName: String,
    errorType: String,
    errorMessage: String,
    errorCode: String? = null
) {
    val params = mutableMapOf<String, Any>(
        AnalyticsProperties.ERROR_TYPE to errorType,
        AnalyticsProperties.ERROR_MESSAGE to errorMessage
    )
    
    errorCode?.let { params[AnalyticsProperties.ERROR_CODE] = it }
    
    logEvent(
        AnalyticsEventBuilder()
            .setScreenName(screenName)
            .setEventType(EventType.CUSTOM)
            .setEventName(AnalyticsEvents.ERROR_OCCURRED)
            .addParameters(params)
    )
} 