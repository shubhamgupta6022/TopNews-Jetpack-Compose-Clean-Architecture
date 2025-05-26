package com.sgupta.analytics.manager

import com.sgupta.analytics.builder.AnalyticsEventBuilder
import com.sgupta.analytics.model.AnalyticsEvent

/**
 * Analytics Manager interface for all analytics operations
 * This provides a contract for analytics tracking across the application
 */
interface AnalyticsManager {
    
    /**
     * Initialize all analytics providers
     * Should be called in Application onCreate
     */
    suspend fun initialize()
    
    /**
     * Log an analytics event to all active providers
     */
    fun logEvent(event: AnalyticsEvent)
    
    /**
     * Log an event using AnalyticsEventBuilder
     */
    fun logEvent(builder: AnalyticsEventBuilder)
    
    /**
     * Set user properties across all providers
     */
    fun setUserProperties(properties: Map<String, Any>)
    
    /**
     * Set user ID across all providers
     */
    fun setUserId(userId: String?)
    
    /**
     * Clear user data across all providers (useful for logout)
     */
    fun clearUserData()
    
    /**
     * Enable or disable analytics collection across all providers
     */
    fun setAnalyticsEnabled(enabled: Boolean)
    
    /**
     * Flush pending events across all providers
     */
    fun flush()
    
    /**
     * Get active analytics providers
     */
    fun getActiveProviders(): List<String>
    
    /**
     * Check if analytics is enabled
     */
    fun isAnalyticsEnabled(): Boolean
}