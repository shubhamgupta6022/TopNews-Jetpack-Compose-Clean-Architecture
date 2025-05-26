package com.sgupta.analytics.model

/**
 * Represents an analytics event with all necessary information
 * @param screenName The screen where the event occurred
 * @param eventType The type of event (e.g., click, view, custom)
 * @param eventName The specific name of the event
 * @param parameters Additional parameters for the event
 * @param userId Optional user identifier
 * @param timestamp Event timestamp (defaults to current time)
 */
data class AnalyticsEvent(
    val screenName: String,
    val eventType: EventType,
    val eventName: String,
    val parameters: Map<String, Any> = emptyMap(),
    val userId: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Predefined event types for better organization
 */
enum class EventType {
    SCREEN_VIEW,
    CLICK,
    SWIPE,
    SCROLL,
    PURCHASE,
    LOGIN,
    LOGOUT,
    SEARCH,
    SHARE,
    CUSTOM
}

/**
 * Standard parameter keys for consistency across the app
 */
object AnalyticsParams {
    const val ITEM_ID = "item_id"
    const val ITEM_NAME = "item_name"
    const val ITEM_CATEGORY = "item_category"
    const val CONTENT_TYPE = "content_type"
    const val SEARCH_TERM = "search_term"
    const val VALUE = "value"
    const val CURRENCY = "currency"
    const val METHOD = "method"
    const val SUCCESS = "success"
    const val ERROR_MESSAGE = "error_message"
    const val DURATION = "duration"
    const val SOURCE = "source"
    const val DESTINATION = "destination"
} 