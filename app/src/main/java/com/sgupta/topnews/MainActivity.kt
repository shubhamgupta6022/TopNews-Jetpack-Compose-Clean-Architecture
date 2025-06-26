package com.sgupta.topnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sgupta.analytics.manager.AnalyticsManager
import com.sgupta.composite.bottombarnavigation.BottomNavigationBar
import com.sgupta.composite.bottombarnavigation.getBottomBarRoutes
import com.sgupta.composite.home.NewsHomeScreen
import com.sgupta.composite.listing.NewsList
import com.sgupta.composite.newsdetail.NewsDetailScreen
import com.sgupta.composite.reels.ReelsScreen
import com.sgupta.composite.search.SearchScreen
import com.sgupta.composite.splash.SplashScreen
import com.sgupta.core.theme.NewsAppTheme
import com.sgupta.navigation.AppNavigation
import com.sgupta.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var analyticsManager: AnalyticsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                AppNavigation(
                    navigator = navigator,
                    bottomBarRoutes = getBottomBarRoutes(),
                    bottomNavigationBar = { BottomNavigationBar(navigator) },
                    splashScreen = { SplashScreen() },
                    homeScreen = { NewsHomeScreen(analyticsManager = analyticsManager) },
                    listScreen = { country, category ->
                        NewsList(
                            country = country,
                            category = category,
                            analyticsManager = analyticsManager,
                            navigator = navigator
                        )
                    },
                    searchScreen = {
                        SearchScreen(
                            analyticsManager = analyticsManager
                        )
                    },
                    detailScreen = { title, url ->
                        NewsDetailScreen(
                            title = title,
                            url = url,
                            navigator
                        )
                    },
                    reelsScreen = { ReelsScreen() }
                )
            }
        }
    }
}