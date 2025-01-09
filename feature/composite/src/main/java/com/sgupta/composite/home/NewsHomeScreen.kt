package com.sgupta.composite.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sgupta.composite.home.components.ArticleListItem
import com.sgupta.composite.home.components.CategoriesSectionItem
import com.sgupta.composite.home.components.CountriesSectionItem
import com.sgupta.composite.home.components.NewsHeader
import com.sgupta.composite.home.components.TopHeadLineSection
import com.sgupta.composite.home.model.HomeNewsUiModel
import com.sgupta.composite.home.states.HomeScreenViewState
import com.sgupta.core.ViewEvent
import com.sgupta.core.components.LoadingIndicator
import com.sgupta.core.components.SearchBar
import com.sgupta.core.components.SectionHeadline
import com.sgupta.core.theme.LightColors
import com.sgupta.core.theme.NewsAppTheme

@Composable
fun NewsHomeScreen(state: HomeScreenViewState, onEvent: (ViewEvent) -> Unit) {
    val lightColors = remember { LightColors }
    val newsUiModel = state.newsUiModel

    NewsAppTheme {
        // Lifted background modifier to avoid recomposition
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(lightColors.surface)
        ) {
            Column {
                NewsHeader()

                if (state.loading) {
                    LoadingIndicator()
                }

                newsUiModel?.let { model ->
                    // Separate composable for better recomposition scope
                    NewsContent(model, onEvent)
                }
            }
        }
    }
}

@Composable
private fun NewsContent(
    model: HomeNewsUiModel,
    onEvent: (ViewEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        // Using remember for stable arrangements
        verticalArrangement = remember { Arrangement.Top },
        horizontalAlignment = remember { Alignment.CenterHorizontally }
    ) {
        item {
            SearchBar(Modifier.padding(horizontal = 16.dp))
        }

        if (model.topNewsItemsList.isNotEmpty()) {
            item {
                SectionHeadline(Modifier, "Top Headlines")
            }
            item {
                TopHeadLineSection(model.topNewsItemsList)
            }
        }

        if (model.articlesItemsList.isNotEmpty()) {
            item {
                SectionHeadline(Modifier, "Articles")
            }
            items(
                model.articlesItemsList.size,
            ) { article ->
                ArticleListItem(model.articlesItemsList[article])
            }
        }

        if (model.categoriesItemsList.isNotEmpty()) {
            item { SectionHeadline(Modifier, "Categories") }
            items(model.categoriesItemsList.size) { category ->
                CategoriesSectionItem(model.categoriesItemsList[category]) {
                    onEvent(it)
                }
            }
        }

        if (model.countriesItemsList.isNotEmpty()) {
            item { SectionHeadline(Modifier, "Countries News") }
            items(model.countriesItemsList.size) { country ->
                CountriesSectionItem(model.countriesItemsList[country]) {
                    onEvent(it)
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}
