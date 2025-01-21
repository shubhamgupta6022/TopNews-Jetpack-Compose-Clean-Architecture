package com.sgupta.composite.listing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sgupta.composite.listing.components.NewsListItem
import com.sgupta.composite.listing.states.NewsListViewState
import com.sgupta.core.components.LoadingIndicator
import com.sgupta.core.components.SearchBar
import com.sgupta.core.components.SectionHeadline
import com.sgupta.core.components.ToolbarComposable

@Composable
fun NewsList(
    state: NewsListViewState,
    navController: NavHostController,
    title: String
) {
    Column {
        ToolbarComposable(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            onBackClick = {
                navController.popBackStack()
            },
            title = title
        )
        when {
            state.newsUiModel?.isNotEmpty() == true -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        SearchBar(Modifier.padding(horizontal = 16.dp))
                    }
                    item {
                        SectionHeadline(Modifier, "Top Headlines")
                    }
                    items(state.newsUiModel.size) { index ->
//                        ArticleListItem(state.newsUiModel[index])
                        NewsListItem(state.newsUiModel[index])
                    }
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