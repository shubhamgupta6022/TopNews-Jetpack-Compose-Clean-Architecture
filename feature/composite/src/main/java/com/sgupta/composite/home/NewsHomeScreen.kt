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
    onEvent: (ViewEvent) -> Unit
) {
    val newsUiModel = state.newsUiModel
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
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
                                ArticleListItem(item)
                            }
                        }

                        if (newsUiModel.categoriesItemsList.isNotEmpty()) {
                            item { SectionHeadline(Modifier, "Categories") }
                            items(
                                newsUiModel.categoriesItemsList,
                                key = { it.categoryType.id }) { category ->
                                CategoriesSectionItem(category) {
                                    onEvent(it)
                                }
                            }
                        }

                        if (newsUiModel.countriesItemsList.isNotEmpty()) {
                            item { SectionHeadline(Modifier, "Countries News") }
                            items(newsUiModel.countriesItemsList, key = { it.id }) { country ->
                                CountriesSectionItem(country) {
                                    onEvent(it)
                                }
                            }
                        }

                        item { Spacer(modifier = Modifier.height(16.dp)) }
                    }
                }

                state.loading -> {
                    LoadingIndicator()
                }

                state.error != null -> {
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
                showSheet = true
            }
            if (showSheet) {
                AIAssistantBottomSheet(
                    sheetState = sheetState,
                    aIAssistantBottomSheetViewState = aIAssistantBottomSheetViewState,
                    onDismiss = { showSheet = false },
                    sendMessageClicked = { message ->
                        onEvent(HomeScreenEvents.GenerateAiContent(message))
                    }
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
        aIAssistantBottomSheetViewState = AIAssistantBottomSheetViewState(aiAssistantChatUiModel = emptyList())
    ) {

    }
}
