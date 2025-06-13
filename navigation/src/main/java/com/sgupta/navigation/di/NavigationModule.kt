package com.sgupta.navigation.di

import com.sgupta.core.navigation.NavigationService
import com.sgupta.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    @Provides
    fun provideNavigationCommander(navigator: Navigator): NavigationService = navigator
}