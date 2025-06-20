package com.sgupta.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sgupta.core.navigation.NewsDestination
import com.sgupta.navigation.destinations.Home
import com.sgupta.navigation.extensions.addHomeScreen
import com.sgupta.navigation.extensions.addListingScreen
import com.sgupta.navigation.extensions.addNewsDetailScreen
import com.sgupta.navigation.extensions.addReelsScreen
import com.sgupta.navigation.extensions.addSearchScreen
import com.sgupta.navigation.extensions.addSplashScreen
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    startDestination: NewsDestination = Home,
    bottomNavigationBar: @Composable () -> Unit,
    splashScreen: @Composable () -> Unit,
    homeScreen: @Composable () -> Unit,
    listScreen: @Composable (String?, String?) -> Unit,
    searchScreen: @Composable () -> Unit,
    detailScreen: @Composable (String, String) -> Unit,
    reelsScreen: @Composable () -> Unit
) {
    val navController = rememberNavController()
    LaunchedEffect(Unit) {
        navigator.actions.collectLatest { action ->
            when (action) {
                Navigator.Action.Back -> navController.popBackStack()
                is Navigator.Action.Navigate -> navController.navigate(
                    route = action.destination,
                    builder = action.navOptions
                )

                is Navigator.Action.PopUpTo -> {
                    navController.popBackStack(
                        route = action.destination,
                        inclusive = action.inclusive
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = bottomNavigationBar
    ) { innerPadding ->
        NavHost(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            startDestination = startDestination.route
        ) {
            addSplashScreen(splashScreen)
            addHomeScreen(homeScreen)
            addListingScreen(listScreen)
            addSearchScreen(searchScreen)
            addNewsDetailScreen(detailScreen)
            addReelsScreen(reelsScreen)
        }
    }
}
