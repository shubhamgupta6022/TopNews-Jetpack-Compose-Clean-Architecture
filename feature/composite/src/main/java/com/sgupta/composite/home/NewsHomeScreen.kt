package com.sgupta.composite.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.sgupta.composite.home.components.CategoriesSectionItem
import com.sgupta.composite.home.components.CountriesSectionItem
import com.sgupta.composite.home.components.NewsHeader
import com.sgupta.composite.home.components.NewsListItem
import com.sgupta.composite.home.components.TopHeadLineSection
import com.sgupta.composite.home.states.HomeScreenViewState
import com.sgupta.core.ViewEvent
import com.sgupta.core.components.LoadingIndicator
import com.sgupta.core.components.SearchBar
import com.sgupta.core.components.SectionHeadline
import com.sgupta.core.theme.LightColors
import com.sgupta.core.theme.NewsAppTheme

@Composable
fun NewsHomeScreen(state: HomeScreenViewState, onEvent: (ViewEvent) -> Unit) {
    val lightColors = LightColors

    val newsUiModel = state.newsUiModel
    val topNewsList = remember(newsUiModel) { newsUiModel?.topNewsItemsList.orEmpty() }
    val articleList = remember(newsUiModel) { newsUiModel?.articlesItemsList.orEmpty() }
    val categoriesList = remember(newsUiModel) { newsUiModel?.categoriesItemsList.orEmpty() }
    val countriesList = remember(newsUiModel) { newsUiModel?.countriesItemsList.orEmpty() }

    NewsAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightColors.surface)
        ) {
            NewsHeader()

            if (state.loading) {
                LoadingIndicator()
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (topNewsList.isNotEmpty()) {
                    item { SearchBar(modifier = Modifier.padding(horizontal = 16.dp)) }
                    item { SectionHeadline(Modifier, "Top Headlines") }
                    item { TopHeadLineSection(topNewsList) }
                }

                items(articleList.size) { index ->
                    NewsListItem(articleList[index])
                }

                if (categoriesList.isNotEmpty()) {
                    item { SectionHeadline(Modifier, "Categories") }
                    items(categoriesList.size) { category ->
                        CategoriesSectionItem(categoriesList[category]) {
                            onEvent(it)
                        }
                    }
                }

                if (countriesList.isNotEmpty()) {
                    item { SectionHeadline(Modifier, "Countries News") }
                    items(countriesList.size) { country ->
                        CountriesSectionItem(countriesList[country]) {
                            onEvent(it)
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}
