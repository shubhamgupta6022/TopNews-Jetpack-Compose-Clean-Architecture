package com.sgupta.analytics.config

/**
 * Configuration class for analytics providers
 * This allows for easy management of which providers are enabled
 * and specific configurations for each provider
 */
data class AnalyticsConfig(
    val firebaseEnabled: Boolean = true,
    val googleAnalyticsEnabled: Boolean = false,
    val cleverTapEnabled: Boolean = false,
    val debugMode: Boolean = false,
    val enableCrashReporting: Boolean = true,
    val enablePerformanceMonitoring: Boolean = true,
    val eventFilterConfig: EventFilterConfig = EventFilterConfig()
)

/**
 * Configuration for filtering events by provider
 * This allows certain events to be sent only to specific providers
 */
data class EventFilterConfig(
    val firebaseOnlyEvents: Set<String> = emptySet(),
    val googleAnalyticsOnlyEvents: Set<String> = emptySet(),
    val cleverTapOnlyEvents: Set<String> = emptySet(),
    val excludedEvents: Set<String> = emptySet()
)

/**
 * Provider identifiers for easy reference
 */
enum class AnalyticsProviderType {
    FIREBASE,
    GOOGLE_ANALYTICS,
    CLEVER_TAP,
    MIXPANEL,
    AMPLITUDE
} 