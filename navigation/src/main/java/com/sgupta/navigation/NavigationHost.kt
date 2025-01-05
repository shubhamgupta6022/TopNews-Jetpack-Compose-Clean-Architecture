package com.sgupta.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sgupta.composite.home.HomeScreenViewModel
import com.sgupta.composite.home.NewsHomeScreen
import com.sgupta.composite.home.events.HomeScreenEvents
import com.sgupta.composite.listing.NewsList

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Home.id) {
        composable(Screens.Home.id) {
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            NewsHomeScreen(
                state = viewModel.states,
                onEvent = {
                    when (it) {
                        is HomeScreenEvents.CountriesViewAllClicked -> {
                            navController.navigate(Screens.Listing.id)
                        }
                    }
                }
            )
        }
        composable(route = Screens.Listing.id) {
            NewsList(navController)
        }
    }
}