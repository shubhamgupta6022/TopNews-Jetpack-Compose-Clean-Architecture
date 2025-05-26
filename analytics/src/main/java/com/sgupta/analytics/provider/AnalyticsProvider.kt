package com.sgupta.analytics.provider

import com.sgupta.analytics.model.AnalyticsEvent

/**
 * Interface for all analytics providers (Firebase, Google Analytics, CleverTap, etc.)
 * Each provider implementation should handle the specific SDK integration
 */
interface AnalyticsProvider {
    
    /**
     * Initialize the analytics provider
     * This will be called during app initialization
     */
    suspend fun initialize()
    
    /**
     * Log an analytics event
     * @param event The analytics event to log
     */
    suspend fun logEvent(event: AnalyticsEvent)
    
    /**
     * Set user properties
     * @param properties Map of user properties to set
     */
    suspend fun setUserProperties(properties: Map<String, Any>)
    
    /**
     * Set user ID for analytics tracking
     * @param userId The user identifier
     */
    suspend fun setUserId(userId: String?)
    
    /**
     * Clear user data (useful for logout scenarios)
     */
    suspend fun clearUserData()
    
    /**
     * Enable or disable analytics collection
     * @param enabled Whether analytics should be enabled
     */
    suspend fun setAnalyticsEnabled(enabled: Boolean)
    
    /**
     * Flush any pending events (useful before app termination)
     */
    suspend fun flush()
    
    /**
     * Get the name of this analytics provider
     */
    fun getProviderName(): String
    
    /**
     * Check if this provider is currently enabled
     */
    fun isEnabled(): Boolean
} 