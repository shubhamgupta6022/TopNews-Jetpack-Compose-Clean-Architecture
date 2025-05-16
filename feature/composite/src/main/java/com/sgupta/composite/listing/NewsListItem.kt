package com.sgupta.composite.listing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.sgupta.composite.listing.components.NewsListItem
import com.sgupta.core.components.LoadingIndicator
import com.sgupta.core.components.ToolbarComposable
import com.sgupta.domain.model.NewsDataModel

@Composable
fun NewsList(
    onBackClick: () -> Unit,
    title: String,
    newsPagingItems: LazyPagingItems<NewsDataModel>?,
) {
    Column {
        ToolbarComposable(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            onBackClick = {
                onBackClick()
            },
            title = title
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
                    newsPagingItems[index]?.let {
                        if (it.articles.isNotEmpty()) {
                            NewsListItem(it.articles[0])
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
                        item(
                            // Key for error state
                            key = "append_error"
                        ) {
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
                        if (newsPagingItems.itemCount == 0) {  // Only show if no items
                            item(
                                key = "refresh_error"
                            ) {
                                // Your error handling UI
                            }
                        }
                    }
                    else -> {}
                }
            }
        }


//        if (newsPagingItems.loadState.refresh is LoadState.Loading) {
//            LoadingIndicator()
//        } else {
//            LazyColumn(modifier = Modifier.fillMaxSize()) {
//                items(newsPagingItems.itemCount) { index ->
//                    newsPagingItems[index]?.let {
//                        NewsListItem(it.articles.get(0))
//                    }
//                }
//
//            }
//        }
//        when {
//            state.newsUiModel?.isNotEmpty() == true -> {
//                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    items(newsPagingItems.itemCount) { index ->
//                        NewsListItem(state.newsUiModel[index])
//                    }
//                }
//            }
//            state.loading -> {
//                LoadingIndicator()
//            }
//            state.error != null -> {
//                Text("Error: ${state.error.message}")
//            }
//            else -> {
//                Text("No news available")
//            }
//        }
    }
}