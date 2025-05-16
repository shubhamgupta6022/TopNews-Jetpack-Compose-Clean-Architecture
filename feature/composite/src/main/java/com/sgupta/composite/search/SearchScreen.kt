package com.sgupta.composite.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgupta.composite.home.components.ArticleListItem
import com.sgupta.composite.search.components.SearchHeader
import com.sgupta.composite.search.events.SearchScreenEvents
import com.sgupta.composite.search.states.SearchScreenViewState
import com.sgupta.core.components.LoadingIndicator
import com.sgupta.core.theme.colorGreyLightBorder

@Composable
fun SearchScreen(
    state: SearchScreenViewState,
    onBackClick: () -> Unit,
    onEvent: (SearchScreenEvents) -> Unit
) {
    val articleModel = state.newsUiModel
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        SearchHeader(
            onBackClick = onBackClick,
            focusRequester = focusRequester,
            onSearch = { query ->
                Log.d("Shubhamtest", "in seachScreen")
                onEvent(SearchScreenEvents.SearchQuery(query))
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colorGreyLightBorder)
        )
        Box {
            when {
                articleModel != null -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        if (articleModel.isNotEmpty()) {
                            items(
                                articleModel,
                                key = { it.title.orEmpty() }) { item ->
                                ArticleListItem(item)
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(state = SearchScreenViewState(), onBackClick = {}, onEvent = {})
}