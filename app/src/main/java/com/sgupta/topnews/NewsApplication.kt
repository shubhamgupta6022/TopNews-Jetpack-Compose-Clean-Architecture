package com.sgupta.topnews

import android.app.Application
import com.sgupta.analytics.manager.AnalyticsManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class NewsApplication : Application() {
    
    @Inject
    lateinit var analyticsManager: AnalyticsManager
    
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize analytics
        applicationScope.launch {
            analyticsManager.initialize()
        }
    }
}