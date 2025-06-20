package com.example.media.utils

object MediaPlayerConstants {
    const val CONNECTION_TIMEOUT = 30000
    const val READ_TIMEOUT = 30000
    const val SEEK_FORWARD_INCREMENT = 10000L
    const val SEEK_BACK_INCREMENT = 10000L
    
    // Player States
    const val REPEAT_MODE_OFF = 0
    const val REPEAT_MODE_ONE = 1
    const val REPEAT_MODE_ALL = 2
    
    // Video Quality
    const val QUALITY_AUTO = -1
    const val QUALITY_144P = 144
    const val QUALITY_240P = 240
    const val QUALITY_360P = 360
    const val QUALITY_480P = 480
    const val QUALITY_720P = 720
    const val QUALITY_1080P = 1080
}