package com.sgupta.composite.search.events

import com.sgupta.core.ViewEvent

sealed class SearchScreenEvents : ViewEvent {
    data class SearchQuery(val query: String) : SearchScreenEvents()
}