package com.sgupta.composite.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgupta.composite.search.events.SearchScreenEvents
import com.sgupta.composite.search.states.SearchScreenViewState
import com.sgupta.core.network.Resource
import com.sgupta.domain.usecase.GetNewsSearchQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getNewsSearchQueryUseCase: GetNewsSearchQueryUseCase
) : ViewModel() {

    var states by mutableStateOf(SearchScreenViewState())
        private set

    private val _searchQuery = MutableStateFlow("")
    private val SEARCH_DEBOUNCE_DELAY = 300L

    init {
        observeSearchQuery()
    }

    fun onEvent(event: SearchScreenEvents) {
        when (event) {
            is SearchScreenEvents.SearchQuery -> {
                _searchQuery.value = event.query
            }
        }
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
                        states = states.copy(loading = true)
                    }

                    is Resource.Success -> {
                        states = states.copy(
                            loading = false,
                            newsUiModel = it.data?.articles.orEmpty()
                        )
                    }

                    is Resource.Error -> {
                        states = states.copy(
                            loading = false,
                            error = it.error
                        )
                    }

                    else -> {}
                }
            }
    }
}
