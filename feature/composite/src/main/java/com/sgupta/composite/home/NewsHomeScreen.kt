package com.sgupta.composite.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sgupta.analytics.constants.AnalyticsEvents
import com.sgupta.analytics.constants.AnalyticsProperties
import com.sgupta.analytics.constants.AnalyticsScreens
import com.sgupta.analytics.extensions.TrackScreenView
import com.sgupta.analytics.extensions.logAIAssistantMessage
import com.sgupta.analytics.extensions.logButtonClick
import com.sgupta.analytics.extensions.logError
import com.sgupta.analytics.extensions.logNewsArticleClick
import com.sgupta.analytics.manager.AnalyticsManager
import com.sgupta.analytics.manager.MockAnalyticsManager
import com.sgupta.composite.R
import com.sgupta.composite.aiassistant.AIAssistantBottomSheet
import com.sgupta.composite.aiassistant.states.AIAssistantBottomSheetViewState
import com.sgupta.composite.home.components.ArticleListItem
import com.sgupta.composite.home.components.CategoriesSectionItem
import com.sgupta.composite.home.components.ChatFloatingActionBtn
import com.sgupta.composite.home.components.CountriesSectionItem
import com.sgupta.composite.home.components.NewsHeader
import com.sgupta.composite.home.events.HomeScreenEvents
import com.sgupta.composite.home.model.HomeNewsUiModel
import com.sgupta.composite.home.states.HomeScreenViewState
import com.sgupta.core.state.ViewEvent
import com.sgupta.core.components.toolbar.common.LoadingIndicator
import com.sgupta.core.components.toolbar.common.SectionHeadline

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsHomeScreen(
    state: HomeScreenViewState,
    aIAssistantBottomSheetViewState: AIAssistantBottomSheetViewState,
    onEvent: (ViewEvent) -> Unit,
    analyticsManager: AnalyticsManager
) {
    val newsUiModel = state.newsUiModel
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }

    // Track screen view with additional properties
    analyticsManager.TrackScreenView(
        screenName = AnalyticsScreens.HOME_SCREEN,
        additionalProperties = mapOf(
            AnalyticsProperties.FEATURE_NAME to "home_feed",
            "has_news_data" to (newsUiModel != null),
            "news_sections_count" to if (newsUiModel != null) {
                listOf(
                    newsUiModel.topNewsItemsList.isNotEmpty(),
                    newsUiModel.categoriesItemsList.isNotEmpty(),
                    newsUiModel.countriesItemsList.isNotEmpty()
                ).count { it }
            } else 0
        )
    )

    Column {
        NewsHeader()
        Box {
            when {
                newsUiModel != null -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            Image(
                                painter = painterResource(R.drawable.ic_search_bar_layout),
                                contentDescription = "Search bar layout",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                                    .clickable {
                                        analyticsManager.logButtonClick(
                                            screenName = AnalyticsScreens.HOME_SCREEN,
                                            buttonName = "search_bar",
                                            buttonType = "navigation"
                                        )
                                        onEvent(HomeScreenEvents.SearchBarClicked)
                                    }
                            )
                        }
                        if (newsUiModel.topNewsItemsList.isNotEmpty()) {
                            item {
                                SectionHeadline(Modifier, "Top Headlines")
                            }
                            items(
                                newsUiModel.topNewsItemsList,
                                key = { it.title.orEmpty() }) { item ->
                                ArticleListItem(
                                    articleDataModel = item,
                                    onItemClick = { event ->
                                        // Log news article click
                                        analyticsManager.logNewsArticleClick(
                                            screenName = AnalyticsScreens.HOME_SCREEN,
                                            title = item.title.orEmpty(),
                                            url = item.url.orEmpty(),
                                            source = item.source?.name,
                                            category = "top_headlines",
                                            position = newsUiModel.topNewsItemsList.indexOf(item)
                                        )
                                        onEvent(event)
                                    }
                                )
                            }
                        }

                        if (newsUiModel.categoriesItemsList.isNotEmpty()) {
                            item { SectionHeadline(Modifier, "Categories") }
                            items(
                                newsUiModel.categoriesItemsList,
                                key = { it.categoryType.id }) { category ->
                                CategoriesSectionItem(
                                    category = category,
                                    onEvent = { event ->
                                        analyticsManager.logEvent(
                                            com.sgupta.analytics.builder.AnalyticsEventBuilder()
                                                .setScreenName(AnalyticsScreens.HOME_SCREEN)
                                                .setEventType(com.sgupta.analytics.model.EventType.CLICK)
                                                .setEventName(AnalyticsEvents.NEWS_CATEGORY_CLICKED)
                                                .addParameter(AnalyticsProperties.NEWS_CATEGORY, category.categoryType.id)
                                                .addParameter(AnalyticsProperties.LIST_POSITION, newsUiModel.categoriesItemsList.indexOf(category))
                                        )
                                        onEvent(event)
                                    }
                                )
                            }
                        }

                        if (newsUiModel.countriesItemsList.isNotEmpty()) {
                            item { SectionHeadline(Modifier, "Countries News") }
                            items(newsUiModel.countriesItemsList, key = { it.id }) { country ->
                                CountriesSectionItem(
                                    countriesUiModel = country,
                                    onEvent = { event ->
                                        analyticsManager.logEvent(
                                            com.sgupta.analytics.builder.AnalyticsEventBuilder()
                                                .setScreenName(AnalyticsScreens.HOME_SCREEN)
                                                .setEventType(com.sgupta.analytics.model.EventType.CLICK)
                                                .setEventName(AnalyticsEvents.NEWS_COUNTRY_CLICKED)
                                                .addParameter(AnalyticsProperties.NEWS_COUNTRY, country.id)
                                                .addParameter(AnalyticsProperties.LIST_POSITION, newsUiModel.countriesItemsList.indexOf(country))
                                        )
                                        onEvent(event)
                                    }
                                )
                            }
                        }

                        item { Spacer(modifier = Modifier.height(16.dp)) }
                    }
                }

                state.loading -> {
                    LoadingIndicator()
                }

                state.error != null -> {
                    // Log error occurrence
                    analyticsManager.logError(
                        screenName = AnalyticsScreens.HOME_SCREEN,
                        errorType = "api_error",
                        errorMessage = state.error.message ?: "Unknown error"
                    )
                    Text("Error: ${state.error.message}")
                }

                else -> {
                    Text("No news available")
                }
            }
            ChatFloatingActionBtn(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                analyticsManager.logButtonClick(
                    screenName = AnalyticsScreens.HOME_SCREEN,
                    buttonName = "ai_assistant_fab",
                    buttonType = "floating_action"
                )
                showSheet = true
            }
            if (showSheet) {
                AIAssistantBottomSheet(
                    sheetState = sheetState,
                    aIAssistantBottomSheetViewState = aIAssistantBottomSheetViewState,
                    onDismiss = { 
                        analyticsManager.logEvent(
                            com.sgupta.analytics.builder.AnalyticsEventBuilder()
                                .setScreenName(AnalyticsScreens.AI_ASSISTANT_BOTTOM_SHEET)
                                .setEventType(com.sgupta.analytics.model.EventType.CUSTOM)
                                .setEventName(AnalyticsEvents.AI_ASSISTANT_CLOSED)
                        )
                        showSheet = false 
                    },
                    sendMessageClicked = { message ->
                        analyticsManager.logAIAssistantMessage(
                            screenName = AnalyticsScreens.AI_ASSISTANT_BOTTOM_SHEET,
                            messageLength = message.length,
                            conversationTurn = aIAssistantBottomSheetViewState.aiAssistantChatUiModel?.size ?: 0
                        )
                        onEvent(HomeScreenEvents.GenerateAiContent(message))
                    },
                    analyticsManager = analyticsManager
                )
            }
        }
    }

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun NewsHomeScreenPreview() {
    NewsHomeScreen(
        state = HomeScreenViewState(newsUiModel = HomeNewsUiModel()),
        aIAssistantBottomSheetViewState = AIAssistantBottomSheetViewState(aiAssistantChatUiModel = emptyList()),
        onEvent = {},
        analyticsManager = MockAnalyticsManager()
    )
}
