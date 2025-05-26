package com.sgupta.analytics.manager

import com.sgupta.analytics.builder.AnalyticsEventBuilder
import com.sgupta.analytics.model.AnalyticsEvent

/**
 * Mock implementation of AnalyticsManager for use in Compose previews and testing
 * This implementation does nothing and doesn't log any events
 */
class MockAnalyticsManager : AnalyticsManager {
    
    override suspend fun initialize() {
        // No-op for mock
    }
    
    override fun logEvent(event: AnalyticsEvent) {
        // No-op for mock - doesn't actually log events
    }
    
    override fun logEvent(builder: AnalyticsEventBuilder) {
        // No-op for mock - doesn't actually log events
    }
    
    override fun setUserProperties(properties: Map<String, Any>) {
        // No-op for mock
    }
    
    override fun setUserId(userId: String?) {
        // No-op for mock
    }
    
    override fun clearUserData() {
        // No-op for mock
    }
    
    override fun setAnalyticsEnabled(enabled: Boolean) {
        // No-op for mock
    }
    
    override fun flush() {
        // No-op for mock
    }
    
    override fun getActiveProviders(): List<String> {
        return emptyList() // Mock returns empty list
    }
    
    override fun isAnalyticsEnabled(): Boolean {
        return false // Mock returns false
    }
}