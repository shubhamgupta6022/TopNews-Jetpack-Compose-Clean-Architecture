package com.sgupta.composite.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sgupta.analytics.constants.AnalyticsProperties
import com.sgupta.analytics.constants.AnalyticsScreens
import com.sgupta.analytics.extensions.TrackScreenView
import com.sgupta.analytics.extensions.logButtonClick
import com.sgupta.analytics.extensions.logError
import com.sgupta.analytics.extensions.logNewsArticleClick
import com.sgupta.analytics.extensions.logSearchQuery
import com.sgupta.analytics.manager.AnalyticsManager
import com.sgupta.analytics.manager.MockAnalyticsManager
import com.sgupta.composite.home.components.ArticleListItem
import com.sgupta.composite.search.events.SearchScreenEvents
import com.sgupta.core.components.toolbar.GenericToolbar
import com.sgupta.core.components.toolbar.common.LoadingIndicator
import com.sgupta.core.components.toolbar.model.ToolbarContent
import com.sgupta.core.components.toolbar.utils.ToolbarDefaults

@Composable
fun SearchScreen(
    analyticsManager: AnalyticsManager
) {
    val viewModel: SearchScreenViewModel = hiltViewModel()
    val state = viewModel.uiState.collectAsState()
    val articleModel = state.value.newsUiModel
    val focusRequester = remember { FocusRequester() }
    
    // Track search screen view
    analyticsManager.TrackScreenView(
        screenName = AnalyticsScreens.SEARCH_SCREEN,
        additionalProperties = mapOf(
            AnalyticsProperties.FEATURE_NAME to "news_search",
            "has_search_results" to (articleModel?.isNotEmpty() == true),
            "results_count" to (articleModel?.size ?: 0)
        )
    )
    
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        GenericToolbar(
            navigationIcon = ToolbarDefaults.backButton {
                analyticsManager.logButtonClick(
                    screenName = AnalyticsScreens.SEARCH_SCREEN,
                    buttonName = "back_button",
                    buttonType = "navigation"
                )
                viewModel.onEvent(SearchScreenEvents.BackClicked)
            },
            content = ToolbarContent.SearchBar(
                placeholder = "Search News...",
                onSearch = { query ->
                    if (query.isNotBlank()) {
                        analyticsManager.logSearchQuery(
                            screenName = AnalyticsScreens.SEARCH_SCREEN,
                            query = query,
                            resultsCount = articleModel?.size
                        )
                    }
                    viewModel.onEvent(SearchScreenEvents.SearchQuery(query = query))
                },
                focusRequester = focusRequester
            )
        )
        Box {
            when {
                articleModel != null -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        if (articleModel.isNotEmpty()) {
                            items(
                                articleModel,
                                key = { it.title.orEmpty() }) { item ->
                                ArticleListItem(
                                    articleDataModel = item,
                                    onItemClick = { event ->
                                        // Log search result click
                                        analyticsManager.logNewsArticleClick(
                                            screenName = AnalyticsScreens.SEARCH_SCREEN,
                                            title = item.title.orEmpty(),
                                            url = item.url.orEmpty(),
                                            source = item.source?.name,
                                            category = "search_result",
                                            position = articleModel.indexOf(item)
                                        )
                                        viewModel.onEvent(SearchScreenEvents.NewsItemClicked(item.title.orEmpty(), item.url.orEmpty()))
                                    }
                                )
                            }
                        }
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                    }
                }

                state.value.loading -> {
                    LoadingIndicator()
                }

                state.value.error != null -> {
                    // Log search error
                    analyticsManager.logError(
                        screenName = AnalyticsScreens.SEARCH_SCREEN,
                        errorType = "search_error",
                        errorMessage = state.value.error?.message ?: "Unknown search error"
                    )
                    Text("Error: ${state.value.error?.message}")
                }

                else -> {
                    Text("No news available")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(analyticsManager = MockAnalyticsManager())
}