package com.sgupta.analytics.manager

import android.util.Log
import com.sgupta.analytics.builder.AnalyticsEventBuilder
import com.sgupta.analytics.config.AnalyticsConfig
import com.sgupta.analytics.model.AnalyticsEvent
import com.sgupta.analytics.provider.AnalyticsProvider
import com.sgupta.analytics.provider.impl.FirebaseAnalyticsProvider
import com.sgupta.analytics.provider.impl.GoogleAnalyticsProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Main Analytics Manager that orchestrates all analytics providers
 * This is the single point of contact for the feature layer
 */
@Singleton
class AnalyticsManagerImpl @Inject constructor(
    private val firebaseProvider: FirebaseAnalyticsProvider,
    private val googleAnalyticsProvider: GoogleAnalyticsProvider,
    private val config: AnalyticsConfig
) : AnalyticsManager {
    
    companion object {
        private const val TAG = "AnalyticsManager"
    }
    
    private val analyticsScope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "Analytics error", throwable)
        }
    )
    
    private val activeProviders = mutableListOf<AnalyticsProvider>()
    
    private var currentUserId: String? = null
    private var isInitialized = false
    
    /**
     * Initialize all analytics providers
     * Should be called in Application onCreate
     */
    override suspend fun initialize() {
        if (isInitialized) return
        
        analyticsScope.launch {
            try {
                // Add enabled providers
                if (config.firebaseEnabled) {
                    activeProviders.add(firebaseProvider)
                }
                if (config.googleAnalyticsEnabled) {
                    activeProviders.add(googleAnalyticsProvider)
                }
                
                // Initialize all active providers
                activeProviders.forEach { provider ->
                    try {
                        provider.initialize()
                        logDebug("Initialized ${provider.getProviderName()}")
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to initialize ${provider.getProviderName()}", e)
                    }
                }
                
                isInitialized = true
                logDebug("Analytics Manager initialized with ${activeProviders.size} providers")
                
            } catch (e: Exception) {
                Log.e(TAG, "Failed to initialize Analytics Manager", e)
            }
        }
    }
    
    /**
     * Log an analytics event to all active providers
     */
    override fun logEvent(event: AnalyticsEvent) {
        if (!isInitialized) {
            Log.w(TAG, "Analytics Manager not initialized. Event will be ignored: ${event.eventName}")
            return
        }
        
        analyticsScope.launch {
            val filteredProviders = getProvidersForEvent(event.eventName)
            
            filteredProviders.forEach { provider ->
                try {
                    if (provider.isEnabled()) {
                        provider.logEvent(event)
                        logDebug("Event '${event.eventName}' sent to ${provider.getProviderName()}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to log event to ${provider.getProviderName()}", e)
                }
            }
        }
    }
    
    /**
     * Log an event using AnalyticsEventBuilder
     */
    override fun logEvent(builder: AnalyticsEventBuilder) {
        try {
            val event = builder.setUserId(currentUserId).build()
            logEvent(event)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to build and log event", e)
        }
    }
    
    /**
     * Set user properties across all providers
     */
    override fun setUserProperties(properties: Map<String, Any>) {
        analyticsScope.launch {
            activeProviders.forEach { provider ->
                try {
                    if (provider.isEnabled()) {
                        provider.setUserProperties(properties)
                        logDebug("User properties set for ${provider.getProviderName()}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to set user properties for ${provider.getProviderName()}", e)
                }
            }
        }
    }
    
    /**
     * Set user ID across all providers
     */
    override fun setUserId(userId: String?) {
        currentUserId = userId
        analyticsScope.launch {
            activeProviders.forEach { provider ->
                try {
                    if (provider.isEnabled()) {
                        provider.setUserId(userId)
                        logDebug("User ID set for ${provider.getProviderName()}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to set user ID for ${provider.getProviderName()}", e)
                }
            }
        }
    }
    
    /**
     * Clear user data across all providers (useful for logout)
     */
    override fun clearUserData() {
        currentUserId = null
        analyticsScope.launch {
            activeProviders.forEach { provider ->
                try {
                    if (provider.isEnabled()) {
                        provider.clearUserData()
                        logDebug("User data cleared for ${provider.getProviderName()}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to clear user data for ${provider.getProviderName()}", e)
                }
            }
        }
    }
    
    /**
     * Enable or disable analytics collection across all providers
     */
    override fun setAnalyticsEnabled(enabled: Boolean) {
        analyticsScope.launch {
            activeProviders.forEach { provider ->
                try {
                    provider.setAnalyticsEnabled(enabled)
                    logDebug("Analytics ${if (enabled) "enabled" else "disabled"} for ${provider.getProviderName()}")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to set analytics enabled for ${provider.getProviderName()}", e)
                }
            }
        }
    }
    
    /**
     * Flush pending events across all providers
     */
    override fun flush() {
        analyticsScope.launch {
            activeProviders.forEach { provider ->
                try {
                    provider.flush()
                    logDebug("Flushed events for ${provider.getProviderName()}")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to flush events for ${provider.getProviderName()}", e)
                }
            }
        }
    }
    
    /**
     * Get active analytics providers
     */
    override fun getActiveProviders(): List<String> {
        return activeProviders.map { it.getProviderName() }
    }
    
    /**
     * Check if analytics is enabled
     */
    override fun isAnalyticsEnabled(): Boolean {
        return activeProviders.any { it.isEnabled() }
    }

    /**
     * Get providers that should receive a specific event based on filtering configuration
     */
    private fun getProvidersForEvent(eventName: String): List<AnalyticsProvider> {
        val filterConfig = config.eventFilterConfig
        
        // Check if event is excluded
        if (filterConfig.excludedEvents.contains(eventName)) {
            return emptyList()
        }
        
        // Check for provider-specific events
        return activeProviders.filter { provider ->
            when (provider.getProviderName()) {
                "Firebase Analytics" -> {
                    filterConfig.googleAnalyticsOnlyEvents.contains(eventName).not() &&
                    filterConfig.cleverTapOnlyEvents.contains(eventName).not()
                }
                "Google Analytics" -> {
                    filterConfig.firebaseOnlyEvents.contains(eventName).not() &&
                    filterConfig.cleverTapOnlyEvents.contains(eventName).not()
                }
                else -> true
            }
        }
    }
    
    private fun logDebug(message: String) {
        if (config.debugMode) {
            Log.d(TAG, message)
        }
    }
} 