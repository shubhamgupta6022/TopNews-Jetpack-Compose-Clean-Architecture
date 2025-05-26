package com.sgupta.analytics.di

import com.sgupta.analytics.config.AnalyticsConfig
import com.sgupta.analytics.config.EventFilterConfig
import com.sgupta.analytics.manager.AnalyticsManager
import com.sgupta.analytics.manager.AnalyticsManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing analytics dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {
    
    /**
     * Binds AnalyticsManagerImpl to AnalyticsManager interface
     */
    @Binds
    @Singleton
    abstract fun bindAnalyticsManager(analyticsManagerImpl: AnalyticsManagerImpl): AnalyticsManager
    
    companion object {
        /**
         * Provides analytics configuration
         * This can be modified based on build variants, remote config, or user preferences
         */
        @Provides
        @Singleton
        fun provideAnalyticsConfig(): AnalyticsConfig {
            return AnalyticsConfig(
                firebaseEnabled = true,
                googleAnalyticsEnabled = false, // Enable when needed
                cleverTapEnabled = false, // Enable when needed
                debugMode = true, // Set to false in production
                enableCrashReporting = true,
                enablePerformanceMonitoring = true,
                eventFilterConfig = EventFilterConfig(
                    // Example: Send purchase events only to Firebase
                    firebaseOnlyEvents = setOf("purchase", "subscription"),
                    // Example: Send marketing events only to CleverTap
                    cleverTapOnlyEvents = setOf("marketing_campaign", "push_notification"),
                    // Example: Exclude sensitive events
                    excludedEvents = setOf("debug_event", "test_event")
                )
            )
        }
    }
} 