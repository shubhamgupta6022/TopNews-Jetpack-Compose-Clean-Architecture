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
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getTopNewsUseCase: GetTopNewsUseCase
) : ViewModel() {

    var states by mutableStateOf<HomeScreenViewState>(HomeScreenViewState())

    init {
        getTopNews()
    }

    private fun getTopNews() {
        getTopNewsUseCase.execute(GetTopNewsUseCase.Param("bitcoin", 1, 5))
            .onEach {
                when (it) {
                    is Resource.Loading -> {
                        states = states.copy(loading = true)
                    }
                    is Resource.Success -> {
                        Log.d("HomeScreenViewModel", "getTopNewsUseCase success = ${it.data}")
                        states = states.copy(loading = false)
                    }
                    is Resource.Error -> {
                        Log.d("HomeScreenViewModel", "getTopNewsUseCase error = ${it.error.message}")
                        states = states.copy(loading = false, error = it.error)
                    }
                    else -> {}
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

}