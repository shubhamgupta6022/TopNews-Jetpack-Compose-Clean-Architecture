package com.sgupta.analytics.constants

/**
 * Constants for analytics properties and metadata
 * This extends the base AnalyticsParams with app-specific properties
 */
object AnalyticsProperties {
    
    // News specific properties
    const val NEWS_TITLE = "news_title"
    const val NEWS_URL = "news_url"
    const val NEWS_CATEGORY = "news_category"
    const val NEWS_COUNTRY = "news_country"
    const val NEWS_SOURCE = "news_source"
    const val NEWS_PUBLISHED_DATE = "news_published_date"
    const val NEWS_AUTHOR = "news_author"
    
    // Search properties
    const val SEARCH_QUERY = "search_query"
    const val SEARCH_RESULTS_COUNT = "search_results_count"
    const val SEARCH_FILTERS = "search_filters"
    const val SEARCH_SUGGESTIONS_SHOWN = "search_suggestions_shown"
    
    // Navigation properties
    const val PREVIOUS_SCREEN = "previous_screen"
    const val NAVIGATION_TYPE = "navigation_type"
    const val BACK_BUTTON_TYPE = "back_button_type" // system, toolbar, gesture
    
    // UI interaction properties
    const val BUTTON_NAME = "button_name"
    const val BUTTON_TYPE = "button_type"
    const val COMPONENT_TYPE = "component_type"
    const val LIST_POSITION = "list_position"
    const val TOTAL_ITEMS = "total_items"
    
    // AI Assistant properties
    const val AI_MESSAGE_LENGTH = "ai_message_length"
    const val AI_RESPONSE_LENGTH = "ai_response_length"
    const val AI_CONVERSATION_TURN = "ai_conversation_turn"
    const val AI_SESSION_DURATION = "ai_session_duration"
    
    // Performance properties
    const val LOAD_TIME_MS = "load_time_ms"
    const val API_ENDPOINT = "api_endpoint"
    const val NETWORK_TYPE = "network_type"
    const val DATA_SOURCE = "data_source" // cache, network, local
    
    // Error properties
    const val ERROR_TYPE = "error_type"
    const val ERROR_CODE = "error_code"
    const val ERROR_MESSAGE = "error_message"
    const val ERROR_CONTEXT = "error_context"
    
    // Feature properties
    const val FEATURE_NAME = "feature_name"
    const val FEATURE_VERSION = "feature_version"
    const val USER_TYPE = "user_type"
    
    // Pagination properties
    const val PAGE_NUMBER = "page_number"
    const val ITEMS_PER_PAGE = "items_per_page"
    const val PAGINATION_DIRECTION = "pagination_direction" // next, previous
    
    // Content properties
    const val CONTENT_ID = "content_id"
    const val CONTENT_CATEGORY = "content_category"
    const val CONTENT_TAGS = "content_tags"
    
    // Session properties
    const val SESSION_ID = "session_id"
    const val USER_AGENT = "user_agent"
    const val APP_VERSION = "app_version"
} 