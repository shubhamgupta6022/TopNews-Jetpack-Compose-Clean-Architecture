package com.sgupta.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sgupta.composite.home.HomeScreenViewModel
import com.sgupta.composite.home.NewsHomeScreen
import com.sgupta.composite.home.events.HomeScreenEvents
import com.sgupta.composite.listing.NewsList
import com.sgupta.composite.listing.NewsListViewModel

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
                            navController.run {
                                navigate(
                                    Screens.Listing.withArgs("country" to it.id)
                                )
                            }
                        }

                        is HomeScreenEvents.CategoryFilterClicked -> {
                            navController.navigate(
                                Screens.Listing.withArgs("category" to it.category)
                            )
                        }
                    }
                }
            )
        }
        composable(
            route = "${Screens.Listing.id}?country={country}&category={category}",
            arguments = listOf(
                navArgument("country") { nullable = true; type = NavType.StringType },
                navArgument("category") { nullable = true; type = NavType.StringType }
            )
        ) { backStackEntry ->
            val country = backStackEntry.arguments?.getString("country")
            val category = backStackEntry.arguments?.getString("category")
            var title: String = "News"
            if (country?.isNotEmpty() == true) {
                title = when (country) {
                    "in" -> "India"
                    "us" -> "USA"
                    "uk" -> "UK"
                    else -> "Country News"
                }
            }
            if (category?.isNotEmpty() == true) {
                title = category
            }
            val viewModel =
                hiltViewModel<NewsListViewModel, NewsListViewModel.NewsListViewModelFactory> { factory ->
                    factory.create(country, category)
                }
            val states = viewModel.states
            NewsList(states, navController, title)
        }
    }
}