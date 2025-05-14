package com.sgupta.navigation.destinations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

/**
 * Sealed interface that defines all possible navigation destinations
 */
sealed interface NewsDestination {
    /**
     * The route string used for navigation
     */
    val route: String

    /**
     * Route with argument placeholders replaced with actual values (if applicable)
     */
    fun createRoute(): String = route

    /**
     * Arguments required for this destination
     */
    fun arguments(): List<NamedNavArgument> = emptyList()

    /**
     * Deep links to this destination (if applicable)
     */
    fun deepLinks(): List<NavDeepLink> = emptyList()
}
