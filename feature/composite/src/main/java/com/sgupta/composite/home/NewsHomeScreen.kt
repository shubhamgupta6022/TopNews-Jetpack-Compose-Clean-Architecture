package com.sgupta.composite.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sgupta.composite.home.components.ArticleListItem
import com.sgupta.composite.home.components.CategoriesSectionItem
import com.sgupta.composite.home.components.CountriesSectionItem
import com.sgupta.composite.home.components.NewsHeader
import com.sgupta.composite.home.components.TopHeadLineSection
import com.sgupta.composite.home.states.HomeScreenViewState
import com.sgupta.core.ViewEvent
import com.sgupta.core.components.LoadingIndicator
import com.sgupta.core.components.SearchBar
import com.sgupta.core.components.SectionHeadline

@Composable
fun NewsHomeScreen(state: HomeScreenViewState, onEvent: (ViewEvent) -> Unit) {
    val newsUiModel = state.newsUiModel
    Column {
        NewsHeader()
        when {
            newsUiModel != null -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        SearchBar(Modifier.padding(horizontal = 16.dp))
                    }
                    if (newsUiModel.topNewsItemsList.isNotEmpty()) {
                        item {
                            SectionHeadline(Modifier, "Top Headlines")
                        }
                        item {
                            TopHeadLineSection(newsUiModel.topNewsItemsList)
                        }
                    }

                    items(newsUiModel.topNewsItemsList.size) { index ->
                        ArticleListItem(newsUiModel.topNewsItemsList[index])
                    }

                    if (newsUiModel.categoriesItemsList.isNotEmpty()) {
                        item { SectionHeadline(Modifier, "Categories") }
                        items(newsUiModel.categoriesItemsList.size) { category ->
                            CategoriesSectionItem(newsUiModel.categoriesItemsList[category]) {
                                onEvent(it)
                            }
                        }
                    }

                    if (newsUiModel.countriesItemsList.isNotEmpty()) {
                        item { SectionHeadline(Modifier, "Countries News") }
                        items(newsUiModel.countriesItemsList.size) { country ->
                            CountriesSectionItem(newsUiModel.countriesItemsList[country]) {
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
    }

}
