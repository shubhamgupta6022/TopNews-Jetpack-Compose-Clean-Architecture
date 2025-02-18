package com.sgupta.composite.listing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sgupta.domain.model.NewsDataModel
import com.sgupta.domain.usecase.GetCategoryNewsUseCase
import com.sgupta.domain.usecase.GetCountryNewsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

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

    private val pageSize = 5

    init {
        Log.d("NewsListViewModel", "country = $country $category")
        category?.let {
            getCategoryList()
        }
        country?.let {
            getCountryList()
        }
    }

    var categoryState: Flow<PagingData<NewsDataModel>>? = null
    var countryStates: Flow<PagingData<NewsDataModel>>? = null

    private fun getCountryList() {
        countryStates = getCountryNewsUseCase.execute(GetCountryNewsUseCase.Param(country.orEmpty(), 1, pageSize))
            .cachedIn(viewModelScope)
    }

    private fun getCategoryList() {
        categoryState = getCategoryNewsUseCase.execute(GetCategoryNewsUseCase.Param(category.orEmpty(), 1, pageSize))
            .cachedIn(viewModelScope)
    }

}