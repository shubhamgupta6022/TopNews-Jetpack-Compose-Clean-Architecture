package com.sgupta.analytics.constants

/**
 * Constants for analytics event names
 * This ensures consistent event naming across the entire application
 */
object AnalyticsEvents {
    
    // Screen events
    const val SCREEN_VIEW = "screen_view"
    const val SCREEN_BACK_PRESSED = "screen_back_pressed"
    
    // Navigation events
    const val NAVIGATION_CLICKED = "navigation_clicked"
    const val BACK_BUTTON_CLICKED = "back_button_clicked"
    
    // User interaction events
    const val BUTTON_CLICKED = "button_clicked"
    const val ITEM_CLICKED = "item_clicked"
    const val SEARCH_PERFORMED = "search_performed"
    const val SEARCH_BAR_CLICKED = "search_bar_clicked"
    
    // News specific events
    const val NEWS_ARTICLE_CLICKED = "news_article_clicked"
    const val NEWS_CATEGORY_CLICKED = "news_category_clicked"
    const val NEWS_COUNTRY_CLICKED = "news_country_clicked"
    const val NEWS_SHARED = "news_shared"
    const val NEWS_BOOKMARKED = "news_bookmarked"
    
    // AI Assistant events
    const val AI_ASSISTANT_OPENED = "ai_assistant_opened"
    const val AI_ASSISTANT_CLOSED = "ai_assistant_closed"
    const val AI_MESSAGE_SENT = "ai_message_sent"
    const val AI_RESPONSE_RECEIVED = "ai_response_received"
    
    // Loading and error events
    const val LOADING_STARTED = "loading_started"
    const val LOADING_COMPLETED = "loading_completed"
    const val ERROR_OCCURRED = "error_occurred"
    const val RETRY_CLICKED = "retry_clicked"
    
    // Performance events
    const val SCREEN_LOAD_TIME = "screen_load_time"
    const val API_RESPONSE_TIME = "api_response_time"
    
    // Feature usage
    const val FEATURE_USED = "feature_used"
    const val PAGINATION_TRIGGERED = "pagination_triggered"
} 