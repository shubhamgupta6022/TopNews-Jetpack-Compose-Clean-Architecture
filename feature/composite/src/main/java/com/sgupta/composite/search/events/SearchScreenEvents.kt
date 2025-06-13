package com.sgupta.composite.search.events

import com.sgupta.core.state.ViewEvent

sealed class SearchScreenEvents : ViewEvent {
    data class SearchQuery(val query: String) : SearchScreenEvents()
    data class NewsItemClicked(val title: String, val url: String): SearchScreenEvents()
    object BackClicked : SearchScreenEvents()
}