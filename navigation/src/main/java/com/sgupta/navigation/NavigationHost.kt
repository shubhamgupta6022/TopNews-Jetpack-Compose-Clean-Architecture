package com.sgupta.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sgupta.navigation.destinations.NewsDestination
import com.sgupta.navigation.destinations.Splash
import com.sgupta.navigation.extensions.addHomeScreen
import com.sgupta.navigation.extensions.addListingScreen
import com.sgupta.navigation.extensions.addSearchScreen
import com.sgupta.navigation.extensions.addSplashScreen

@Composable
fun NewsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: NewsDestination = Splash
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {
        addSplashScreen(navController)
        addHomeScreen(navController)
        addListingScreen(navController)
        addSearchScreen(navController)
    }
}
