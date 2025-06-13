package com.sgupta.composite.listing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.sgupta.analytics.constants.AnalyticsEvents
import com.sgupta.analytics.constants.AnalyticsProperties
import com.sgupta.analytics.constants.AnalyticsScreens
import com.sgupta.analytics.extensions.TrackScreenView
import com.sgupta.analytics.extensions.logButtonClick
import com.sgupta.analytics.extensions.logError
import com.sgupta.analytics.extensions.logNewsArticleClick
import com.sgupta.analytics.manager.AnalyticsManager
import com.sgupta.composite.listing.components.NewsListItem
import com.sgupta.core.components.toolbar.GenericToolbar
import com.sgupta.core.components.toolbar.common.LoadingIndicator
import com.sgupta.core.components.toolbar.model.ToolbarContent
import com.sgupta.core.components.toolbar.utils.ToolbarDefaults
import com.sgupta.navigation.Navigator

@Composable
fun NewsList(
    country: String?,
    category: String?,
    analyticsManager: AnalyticsManager,
    navigator: Navigator
) {
    val title = when {
        country?.isNotEmpty() == true -> when (country) {
            "in" -> "India"
            "us" -> "USA"
            "uk" -> "UK"
            else -> "Country News"
        }

        category?.isNotEmpty() == true -> category
        else -> "News"
    }
    val viewModel =
        hiltViewModel<NewsListViewModel, NewsListViewModel.NewsListViewModelFactory> { factory ->
            factory.create(country, category)
        }
    val newsPagingItems = if (country?.isNotEmpty() == true) {
        viewModel.countryStates?.collectAsLazyPagingItems()
    } else {
        viewModel.categoryState?.collectAsLazyPagingItems()
    }
    // Track news list screen view
    analyticsManager.TrackScreenView(
        screenName = AnalyticsScreens.NEWS_LIST_SCREEN,
        additionalProperties = mapOf(
            AnalyticsProperties.FEATURE_NAME to "news_listing",
            "list_title" to title,
            "items_count" to (newsPagingItems?.itemCount ?: 0),
            "loading_state" to (newsPagingItems?.loadState?.refresh is LoadState.Loading)
        )
    )

    Column {
        GenericToolbar(
            navigationIcon = ToolbarDefaults.backButton {
                analyticsManager.logButtonClick(
                    screenName = AnalyticsScreens.NEWS_LIST_SCREEN,
                    buttonName = "back_button",
                    buttonType = "navigation",
                    additionalProperties = mapOf(
                        "list_title" to title
                    )
                )
                navigator.goBack()
            },
            content = ToolbarContent.Title(title)
        )

        newsPagingItems?.let {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                // State hoisting to preserve scroll position
                state = rememberLazyListState()
            ) {
                items(
                    count = newsPagingItems.itemCount,
                ) { index ->
                    newsPagingItems[index]?.let { newsData ->
                        if (newsData.articles.isNotEmpty()) {
                            NewsListItem(
                                articleDataModel = newsData.articles[0],
                                onItemClick = { article ->
                                    // Log news list item click
                                    analyticsManager.logNewsArticleClick(
                                        screenName = AnalyticsScreens.NEWS_LIST_SCREEN,
                                        title = article.title.orEmpty(),
                                        url = article.url.orEmpty(),
                                        source = article.source.name,
                                        category = title,
                                        position = index
                                    )

                                    // Log pagination if near end
                                    if (index >= newsPagingItems.itemCount - 5) {
                                        analyticsManager.logEvent(
                                            com.sgupta.analytics.builder.AnalyticsEventBuilder()
                                                .setScreenName(AnalyticsScreens.NEWS_LIST_SCREEN)
                                                .setEventType(com.sgupta.analytics.model.EventType.CUSTOM)
                                                .setEventName(AnalyticsEvents.PAGINATION_TRIGGERED)
                                                .addParameter(
                                                    AnalyticsProperties.PAGE_NUMBER,
                                                    (index / 20) + 1
                                                )
                                                .addParameter("trigger_position", index)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }

                // Handle loading and error states at the bottom
                when {
                    newsPagingItems.loadState.append is LoadState.Loading -> {
                        item(
                            // Key for loading indicator to prevent unnecessary recomposition
                            key = "append_loading"
                        ) {
                            LoadingIndicator()
                        }
                    }

                    newsPagingItems.loadState.append is LoadState.Error -> {
                        val error = newsPagingItems.loadState.append as LoadState.Error
                        item(
                            // Key for error state
                            key = "append_error"
                        ) {
                            // Log pagination error
                            analyticsManager.logError(
                                screenName = AnalyticsScreens.NEWS_LIST_SCREEN,
                                errorType = "pagination_error",
                                errorMessage = error.error.message ?: "Pagination failed"
                            )
                            // Your error handling UI
                        }
                    }
                }

                // Handle initial loading state
                when (newsPagingItems.loadState.refresh) {
                    is LoadState.Loading -> {
                        if (newsPagingItems.itemCount == 0) {  // Only show if no items
                            item(
                                key = "refresh_loading"
                            ) {
                                LoadingIndicator()
                            }
                        }
                    }

                    is LoadState.Error -> {
                        val error = newsPagingItems.loadState.refresh as LoadState.Error
                        if (newsPagingItems.itemCount == 0) {  // Only show if no items
                            item(
                                key = "refresh_error"
                            ) {
                                // Log initial load error
                                analyticsManager.logError(
                                    screenName = AnalyticsScreens.NEWS_LIST_SCREEN,
                                    errorType = "initial_load_error",
                                    errorMessage = error.error.message ?: "Failed to load news list"
                                )
                                // Your error handling UI
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}