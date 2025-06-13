package com.sgupta.navigation.destinations

import com.sgupta.core.navigation.NewsDestination

/**
 * The home screen destination
 */
data object Home : NewsDestination {
    override val route: String = "home"
}