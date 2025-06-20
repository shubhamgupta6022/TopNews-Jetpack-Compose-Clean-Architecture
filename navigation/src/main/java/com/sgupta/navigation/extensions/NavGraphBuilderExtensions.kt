package com.sgupta.navigation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sgupta.navigation.destinations.Home
import com.sgupta.navigation.destinations.Listing
import com.sgupta.navigation.destinations.NewsDetail
import com.sgupta.navigation.destinations.Reels
import com.sgupta.navigation.destinations.Search
import com.sgupta.navigation.destinations.Splash

/**
 * Add splash screen to navigation graph
 */
internal fun NavGraphBuilder.addSplashScreen(
    splashScreen: @Composable (() -> Unit)
) {
    composable(
        route = Splash.route
    ) {
        splashScreen()
    }
}

/**
 * Add home screen to navigation graph
 */
internal fun NavGraphBuilder.addHomeScreen(
    homeScreen: @Composable (() -> Unit)
) {
    composable(
        route = Home.route
    ) {
        homeScreen()
    }
}

/**
 * Add listing screen to navigation graph
 */
internal fun NavGraphBuilder.addListingScreen(
    listScreen: @Composable ((String?, String?) -> Unit)
) {
    composable(
        route = Listing().route,
        arguments = Listing().arguments()
    ) { backStackEntry ->
        val destination = remember { Listing.fromNavBackStackEntry(backStackEntry) }
        listScreen(destination.country, destination.category)
    }
}

/**
 * Add search screen to navigation graph
 */
internal fun NavGraphBuilder.addSearchScreen(
    searchScreen: @Composable (() -> Unit)
) {
    composable(
        route = Search.route
    ) {
        searchScreen()
    }
}

/**
 * Add news detail screen to navigation graph
 */
internal fun NavGraphBuilder.addNewsDetailScreen(
    detailScreen: @Composable ((String, String) -> Unit)
) {
    composable(
        route = NewsDetail().route,
        arguments = NewsDetail().arguments()
    ) { backStackEntry ->
        val destination = remember { NewsDetail.fromNavBackStackEntry(backStackEntry) }
        detailScreen(destination.title.orEmpty(), destination.url.orEmpty())
    }
}

/**
 * Add news detail screen to navigation graph
 */
internal fun NavGraphBuilder.addReelsScreen(
    reelsScreen: @Composable () -> Unit
) {
    composable(
        route = Reels.route,
    ) {
        reelsScreen()
    }
}