package com.sgupta.analytics.builder

import com.sgupta.analytics.model.AnalyticsEvent
import com.sgupta.analytics.model.EventType

/**
 * Builder class for creating AnalyticsEvent objects with a fluent API
 * Usage:
 * val event = AnalyticsEventBuilder()
 *     .setScreenName("HomeScreen")
 *     .setEventType(EventType.CLICK)
 *     .setEventName("button_clicked")
 *     .addParameter("button_name", "subscribe")
 *     .setUserId("user123")
 *     .build()
 */
class AnalyticsEventBuilder {
    
    private var screenName: String = ""
    private var eventType: EventType = EventType.CUSTOM
    private var eventName: String = ""
    private val parameters = mutableMapOf<String, Any>()
    private var userId: String? = null
    private var timestamp: Long = System.currentTimeMillis()
    
    /**
     * Set the screen name where the event occurred
     */
    fun setScreenName(screenName: String): AnalyticsEventBuilder {
        this.screenName = screenName
        return this
    }
    
    /**
     * Set the type of event
     */
    fun setEventType(eventType: EventType): AnalyticsEventBuilder {
        this.eventType = eventType
        return this
    }
    
    /**
     * Set the specific name of the event
     */
    fun setEventName(eventName: String): AnalyticsEventBuilder {
        this.eventName = eventName
        return this
    }
    
    /**
     * Add a single parameter to the event
     */
    fun addParameter(key: String, value: Any): AnalyticsEventBuilder {
        this.parameters[key] = value
        return this
    }
    
    /**
     * Add multiple parameters to the event
     */
    fun addParameters(params: Map<String, Any>): AnalyticsEventBuilder {
        this.parameters.putAll(params)
        return this
    }
    
    /**
     * Set the user ID for the event
     */
    fun setUserId(userId: String?): AnalyticsEventBuilder {
        this.userId = userId
        return this
    }
    
    /**
     * Set a custom timestamp for the event (optional)
     */
    fun setTimestamp(timestamp: Long): AnalyticsEventBuilder {
        this.timestamp = timestamp
        return this
    }
    
    /**
     * Build the AnalyticsEvent object
     * @throws IllegalArgumentException if required fields are not set
     */
    fun build(): AnalyticsEvent {
        require(screenName.isNotEmpty()) { "Screen name is required" }
        require(eventName.isNotEmpty()) { "Event name is required" }
        
        return AnalyticsEvent(
            screenName = screenName,
            eventType = eventType,
            eventName = eventName,
            parameters = parameters.toMap(),
            userId = userId,
            timestamp = timestamp
        )
    }
    
    companion object {
        /**
         * Quick builders for common event types
         */
        
        fun screenView(screenName: String): AnalyticsEventBuilder {
            return AnalyticsEventBuilder()
                .setScreenName(screenName)
                .setEventType(EventType.SCREEN_VIEW)
                .setEventName("screen_view")
        }
        
        fun buttonClick(screenName: String, buttonName: String): AnalyticsEventBuilder {
            return AnalyticsEventBuilder()
                .setScreenName(screenName)
                .setEventType(EventType.CLICK)
                .setEventName("button_click")
                .addParameter("button_name", buttonName)
        }
        
        fun itemClick(screenName: String, itemId: String, itemName: String): AnalyticsEventBuilder {
            return AnalyticsEventBuilder()
                .setScreenName(screenName)
                .setEventType(EventType.CLICK)
                .setEventName("item_click")
                .addParameter("item_id", itemId)
                .addParameter("item_name", itemName)
        }
        
        fun search(screenName: String, searchTerm: String): AnalyticsEventBuilder {
            return AnalyticsEventBuilder()
                .setScreenName(screenName)
                .setEventType(EventType.SEARCH)
                .setEventName("search")
                .addParameter("search_term", searchTerm)
        }
        
        fun share(screenName: String, contentType: String, method: String): AnalyticsEventBuilder {
            return AnalyticsEventBuilder()
                .setScreenName(screenName)
                .setEventType(EventType.SHARE)
                .setEventName("share")
                .addParameter("content_type", contentType)
                .addParameter("method", method)
        }
    }
} 