package com.sgupta.composite.listing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.sgupta.composite.listing.components.NewsListItem
import com.sgupta.core.components.toolbar.common.LoadingIndicator
import com.sgupta.core.components.toolbar.GenericToolbar
import com.sgupta.core.components.toolbar.model.ToolbarContent
import com.sgupta.core.components.toolbar.utils.ToolbarDefaults
import com.sgupta.domain.model.NewsDataModel

@Composable
fun NewsList(
    onBackClick: () -> Unit,
    title: String,
    newsPagingItems: LazyPagingItems<NewsDataModel>?,
) {
    Column {
        GenericToolbar(
            navigationIcon = ToolbarDefaults.backButton {
                onBackClick()
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
    }
}