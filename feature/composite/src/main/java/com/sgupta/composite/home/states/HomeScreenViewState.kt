package com.sgupta.composite.home.states

import com.sgupta.composite.home.model.HomeNewsUiModel

data class HomeScreenViewState(
    val loading: Boolean = true,
    val newsUiModel: HomeNewsUiModel? = null,
    val error: Throwable? = null
)