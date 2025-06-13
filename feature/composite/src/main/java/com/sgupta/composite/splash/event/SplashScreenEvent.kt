package com.sgupta.composite.splash.event

sealed interface SplashScreenEvent {
    object Loaded: SplashScreenEvent
}