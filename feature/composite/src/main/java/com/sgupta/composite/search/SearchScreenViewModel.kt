package com.sgupta.composite.search

import androidx.lifecycle.viewModelScope
import com.sgupta.composite.search.events.SearchScreenEvents
import com.sgupta.composite.search.states.SearchScreenViewState
import com.sgupta.core.navigation.NavigationService
import com.sgupta.core.network.Resource
import com.sgupta.core.presentation.StateAndEventViewModel
import com.sgupta.domain.usecase.GetNewsSearchQueryUseCase
import com.sgupta.navigation.destinations.NewsDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getNewsSearchQueryUseCase: GetNewsSearchQueryUseCase,
    private val navigator: NavigationService
) : StateAndEventViewModel<SearchScreenViewState, SearchScreenEvents>(SearchScreenViewState()) {

    private val _searchQuery = MutableStateFlow("")
    private val SEARCH_DEBOUNCE_DELAY = 300L

    override suspend fun handleEvent(event: SearchScreenEvents) {
        when (event) {
            is SearchScreenEvents.SearchQuery -> {
                _searchQuery.value = event.query
            }

            SearchScreenEvents.BackClicked -> {
                navigator.goBack()
            }
            is SearchScreenEvents.NewsItemClicked -> {
                navigator.navigateTo(
                    destination = NewsDetail(event.title, event.url)
                )
            }
        }
    }

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(SEARCH_DEBOUNCE_DELAY)
                .distinctUntilChanged()
                .collectLatest {
                    if (it.isNotEmpty()) {
                        getSearchQuery(it)
                    }
                }
        }
    }

    private suspend fun getSearchQuery(query: String) {
        getNewsSearchQueryUseCase.execute(GetNewsSearchQueryUseCase.Param(query))
            .collect {
                when (it) {
                    is Resource.Loading -> {
                        updateUiState {
                            copy(loading = true)
                        }
                    }

                    is Resource.Success -> {
                        updateUiState {
                            copy(
                                loading = false,
                                newsUiModel = it.data?.articles.orEmpty()
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateUiState {
                            copy(
                                loading = false,
                                error = it.error
                            )
                        }
                    }

                    else -> {}
                }
            }
    }
}
