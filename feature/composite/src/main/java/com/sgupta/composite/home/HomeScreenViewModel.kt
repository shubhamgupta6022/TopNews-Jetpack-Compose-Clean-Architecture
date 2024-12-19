package com.sgupta.composite.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgupta.composite.home.states.HomeScreenViewState
import com.sgupta.core.network.Resource
import com.sgupta.domain.usecase.GetTopNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getTopNewsUseCase: GetTopNewsUseCase
) : ViewModel() {

//    private val _uiStates = MutableStateFlow<HomeScreenViewState>(HomeScreenViewState.Loading)
    var states by mutableStateOf<HomeScreenViewState>(HomeScreenViewState.Loading)

//    init {
//        getTopNews()
//    }

    fun getTopNews() {
        getTopNewsUseCase.execute(GetTopNewsUseCase.Param("bitcoin", 1, 1))
            .onEach {
                when(it) {
                    Resource.Loading -> {
                        states = HomeScreenViewState.Loading
                    }
                    is Resource.Success -> {
                        Log.d("HomeScreenViewModel", "getTopNewsUseCase = ${it.data}")
                        states = HomeScreenViewState.ApiSuccess(emptyList())
                    }
                    is Resource.Error -> {
                        Log.d("HomeScreenViewModel", "getTopNewsUseCase = ${it.error.message}")
                    }
                    is Resource.ErrorInSuccess -> {

                    }
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

}