package com.sgupta.composite.listing

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgupta.core.network.Resource
import com.sgupta.domain.usecase.GetCategoryNewsUseCase
import com.sgupta.domain.usecase.GetCountryNewsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel(assistedFactory = NewsListViewModel.NewsListViewModelFactory::class)
class NewsListViewModel @AssistedInject constructor(
    @Assisted("country") val country: String?,
    @Assisted("category") val category: String?,
    private val getCountryNewsUseCase: GetCountryNewsUseCase,
    private val getCategoryNewsUseCase: GetCategoryNewsUseCase
) : ViewModel() {

    @AssistedFactory
    interface NewsListViewModelFactory {
        fun create(
            @Assisted("country") country: String?,
            @Assisted("category") category: String?
        ): NewsListViewModel
    }

    var states by mutableStateOf(NewsListViewState())

    init {
        Log.d("NewsListViewModel", "country = $country $category")
        category?.let {
            getCategoryList()
        }
        country?.let {
            getCountryList()
        }
    }

    private fun getCountryList() {
        getCountryNewsUseCase.execute(GetCountryNewsUseCase.Param("us", 1, 5))
            .onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("NewsListViewModel", "loading")
                        states = states.copy(loading = true)
                    }
                    is Resource.Success -> {
                        Log.d("NewsListViewModel", "success")
                        states = states.copy(newsUiModel = it.data?.articles.orEmpty(), loading = false)
                    }
                    is Resource.Error -> {
                        Log.d("NewsListViewModel", "error")
                        states = states.copy(loading = false, error = it.error)
                    }
                    else -> {}
                }
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    private fun getCategoryList() {
        getCategoryNewsUseCase.execute(GetCategoryNewsUseCase.Param(category.orEmpty(), 1, 5))
            .onEach {
                when (it) {
                    is Resource.Success -> {
                        states = states.copy(newsUiModel = it.data?.articles.orEmpty(), loading = false)
                    }
                    else -> {}
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

}