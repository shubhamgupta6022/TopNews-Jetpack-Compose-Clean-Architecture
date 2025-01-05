package com.sgupta.composite.home.states

data class HomeScreenViewState(
    val loading: Boolean = false,
    val newsList: List<String>? = null,
    val error: Throwable? = null
)