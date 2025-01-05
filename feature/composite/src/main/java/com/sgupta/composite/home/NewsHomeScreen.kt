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
import com.sgupta.composite.home.components.SectionHeader
import com.sgupta.composite.home.components.TopHeadLineSection
import com.sgupta.composite.home.states.HomeScreenViewState
import com.sgupta.core.ViewEvent
import com.sgupta.core.components.SearchBar
import com.sgupta.core.theme.LightColors
import com.sgupta.core.theme.NewsAppTheme

@Composable
fun NewsHomeScreen(state: HomeScreenViewState, onEvent: (ViewEvent) -> Unit) {
    val lightColors = remember { LightColors }
    val handleEvent = remember(onEvent) { { event: ViewEvent -> onEvent(event) } }

    NewsAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightColors.surface)
        ) {
            NewsHeader()

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    SearchBar(modifier = Modifier.padding(horizontal = 16.dp))
                }

                item {
                    TopHeadLineSection()
                }

                items(3) { _ ->
                    NewsListItem()
                }

                item {
                    SectionHeader(title = "Categories", modifier = Modifier.padding(start = 16.dp))
                }
                items(3) { category ->
                    CategoriesSectionItem()
                }

                item {
                    SectionHeader(title = "Countries News", modifier = Modifier.padding(start = 16.dp))
                }
                items(3) { country ->
                    CountriesSectionItem() { handleEvent(it) }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
