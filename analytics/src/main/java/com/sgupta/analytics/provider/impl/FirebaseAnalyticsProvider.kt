package com.sgupta.analytics.provider.impl

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.sgupta.analytics.model.AnalyticsEvent
import com.sgupta.analytics.model.EventType
import com.sgupta.analytics.provider.AnalyticsProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Firebase Analytics implementation of AnalyticsProvider
 */
@Singleton
class FirebaseAnalyticsProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : AnalyticsProvider {
    
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private var isAnalyticsEnabled = true
    
    override suspend fun initialize() {
        withContext(Dispatchers.Main) {
            firebaseAnalytics = Firebase.analytics
            firebaseAnalytics.setAnalyticsCollectionEnabled(isAnalyticsEnabled)
        }
    }
    
    override suspend fun logEvent(event: AnalyticsEvent) {
        if (!isAnalyticsEnabled) return
        
        withContext(Dispatchers.Main) {
            val bundle = Bundle().apply {
                putString("screen_name", event.screenName)
                putString("event_type", event.eventType.name)
                putLong("timestamp", event.timestamp)
                
                // Add custom parameters
                event.parameters.forEach { (key, value) ->
                    when (value) {
                        is String -> putString(key, value)
                        is Int -> putInt(key, value)
                        is Long -> putLong(key, value)
                        is Double -> putDouble(key, value)
                        is Float -> putFloat(key, value)
                        is Boolean -> putBoolean(key, value)
                        else -> putString(key, value.toString())
                    }
                }
            }
            
            // Use Firebase Analytics event name conventions
            val firebaseEventName = mapEventName(event.eventName, event.eventType)
            firebaseAnalytics.logEvent(firebaseEventName, bundle)
        }
    }
    
    override suspend fun setUserProperties(properties: Map<String, Any>) {
        if (!isAnalyticsEnabled) return
        
        withContext(Dispatchers.Main) {
            properties.forEach { (key, value) ->
                firebaseAnalytics.setUserProperty(key, value.toString())
            }
        }
    }
    
    override suspend fun setUserId(userId: String?) {
        if (!isAnalyticsEnabled) return
        
        withContext(Dispatchers.Main) {
            firebaseAnalytics.setUserId(userId)
        }
    }
    
    override suspend fun clearUserData() {
        if (!isAnalyticsEnabled) return
        
        withContext(Dispatchers.Main) {
            firebaseAnalytics.setUserId(null)
            firebaseAnalytics.resetAnalyticsData()
        }
    }
    
    override suspend fun setAnalyticsEnabled(enabled: Boolean) {
        isAnalyticsEnabled = enabled
        withContext(Dispatchers.Main) {
            firebaseAnalytics.setAnalyticsCollectionEnabled(enabled)
        }
    }
    
    override suspend fun flush() {
        // Firebase Analytics handles flushing automatically
        // No explicit flush method available in Firebase Analytics
    }
    
    override fun getProviderName(): String = "Firebase Analytics"
    
    override fun isEnabled(): Boolean = isAnalyticsEnabled
    
    /**
     * Maps custom event names to Firebase Analytics conventions
     */
    private fun mapEventName(eventName: String, eventType: EventType): String {
        return when (eventType) {
            EventType.SCREEN_VIEW -> FirebaseAnalytics.Event.SCREEN_VIEW
            EventType.CLICK -> "select_content"
            EventType.SEARCH -> FirebaseAnalytics.Event.SEARCH
            EventType.SHARE -> FirebaseAnalytics.Event.SHARE
            EventType.PURCHASE -> FirebaseAnalytics.Event.PURCHASE
            EventType.LOGIN -> FirebaseAnalytics.Event.LOGIN
            else -> eventName.lowercase().replace(" ", "_")
        }
    }
} 