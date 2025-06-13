package com.sgupta.navigation.destinations

import com.sgupta.core.navigation.NewsDestination

/**
 * The search screen destination
 */
data object Search : NewsDestination {
    override val route: String = "search"
}