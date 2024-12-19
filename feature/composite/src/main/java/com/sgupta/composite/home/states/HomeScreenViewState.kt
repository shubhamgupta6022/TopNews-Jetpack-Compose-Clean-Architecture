package com.sgupta.composite.home.states

sealed class HomeScreenViewState {
    object Loading : HomeScreenViewState()
    data class ApiSuccess(val data: List<String>) : HomeScreenViewState()
}