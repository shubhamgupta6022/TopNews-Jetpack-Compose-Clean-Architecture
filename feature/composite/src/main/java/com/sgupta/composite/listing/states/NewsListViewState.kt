package com.sgupta.composite.listing.states

import com.sgupta.domain.model.ArticleDataModel

data class NewsListViewState(
    val loading: Boolean = true,
    val newsUiModel: List<ArticleDataModel>? = null,
    val error: Throwable? = null
)