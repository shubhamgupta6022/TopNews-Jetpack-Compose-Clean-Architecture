package com.sgupta.composite.search.states

import com.sgupta.domain.model.ArticleDataModel

data class SearchScreenViewState(
    val loading: Boolean = true,
    val newsUiModel: List<ArticleDataModel>? = null,
    val error: Throwable? = null
)