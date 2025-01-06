package com.sgupta.composite.home.events

import com.sgupta.core.ViewEvent

sealed class HomeScreenEvents : ViewEvent {
    data class CountriesViewAllClicked(val id: String) : HomeScreenEvents()
    data class CategoryFilterClicked(val category: String) : HomeScreenEvents()
}