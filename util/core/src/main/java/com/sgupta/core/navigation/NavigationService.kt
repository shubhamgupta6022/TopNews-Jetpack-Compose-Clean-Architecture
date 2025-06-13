package com.sgupta.core.navigation

import androidx.navigation.NavOptionsBuilder

interface NavigationService {
    fun navigateTo(destination: NewsDestination, navOptions: NavOptionsBuilder.() -> Unit = {})
    fun goBack()
    fun popUpTo(destination: NewsDestination, inclusive: Boolean = false)
}