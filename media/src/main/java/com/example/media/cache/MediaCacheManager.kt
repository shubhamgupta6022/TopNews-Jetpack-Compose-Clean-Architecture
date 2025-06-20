package com.example.media.cache

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@UnstableApi
class MediaCacheManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val CACHE_SIZE = 200 * 1024 * 1024L // 200MB
        private const val CACHE_FOLDER = "media_cache"
    }
    
    private val cacheDir = File(context.cacheDir, CACHE_FOLDER)
    private val cacheEvictor = LeastRecentlyUsedCacheEvictor(CACHE_SIZE)
    
    private var _cache: SimpleCache? = null
    private val cache: SimpleCache
        get() = _cache ?: createCache().also { _cache = it }
    
    private fun createCache(): SimpleCache {
        return SimpleCache(cacheDir, cacheEvictor)
    }
    
    fun getCache(): Cache = cache
    
    suspend fun preloadMedia(url: String) {
        withContext(Dispatchers.IO) {
            try {
                val dataSourceFactory = DefaultHttpDataSource.Factory()
                val cacheDataSourceFactory = CacheDataSource.Factory()
                    .setCache(cache)
                    .setUpstreamDataSourceFactory(dataSourceFactory)
                
                // Implement preloading logic here
                // This is a simplified version - you may want to implement more sophisticated preloading
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    suspend fun clearCache() {
        withContext(Dispatchers.IO) {
            try {
                cache.keys.forEach { key ->
                    cache.removeResource(key)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun getCacheSize(): Long {
        return cache.cacheSpace
    }
    
    fun release() {
        _cache?.release()
        _cache = null
    }
}