package com.sgupta.composite.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgupta.composite.aiassistant.model.AIAssistantChatUiModel
import com.sgupta.composite.aiassistant.states.AIAssistantBottomSheetViewState
import com.sgupta.composite.home.model.HomeNewsUiModel
import com.sgupta.composite.home.states.HomeScreenViewState
import com.sgupta.core.network.Resource
import com.sgupta.domain.model.ArticleDataModel
import com.sgupta.domain.usecase.GetTopNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getTopNewsUseCase: GetTopNewsUseCase
) : ViewModel() {

    var states by mutableStateOf(HomeScreenViewState())
    var aiAssistantBottomSheetStates by mutableStateOf(
        AIAssistantBottomSheetViewState(
            aiAssistantChatUiModel = listOf(
                AIAssistantChatUiModel(isUser = false, message = "How can I help you?"),
                AIAssistantChatUiModel(isUser = true, message = "What are the top news for today"),
            )
        )
    )
    private var topHeadlinesJob: Job? = null
    private var otherTopHeadlinesJob: Job? = null
    private var newsUiModel: HomeNewsUiModel? = null
    private var topNewsItemsList: List<ArticleDataModel>? = null
    private var articlesItemsList: List<ArticleDataModel>? = null

    init {
        getTopNews()
    }

    private fun getTopNews() {
        topHeadlinesJob?.cancel()
        otherTopHeadlinesJob?.cancel()
        states = states.copy(loading = true)
        topHeadlinesJob = topHeadLinesJob()
        otherTopHeadlinesJob = otherTopHeadLines()
        viewModelScope.launch {
            topHeadlinesJob?.join()
            otherTopHeadlinesJob?.join()
            newsUiModel = HomeNewsUiModel().copy(
                topNewsItemsList = topNewsItemsList.orEmpty(),
                articlesItemsList = articlesItemsList.orEmpty()
            )
            states = states.copy(loading = false, newsUiModel = newsUiModel)
        }
    }

    private fun topHeadLinesJob() =
        getTopNewsUseCase.execute(GetTopNewsUseCase.Param("bbc-news", 1, 5))
            .onEach {
                when (it) {
                    is Resource.Success -> {
                        topNewsItemsList = it.data?.articles.orEmpty()
                    }

                    else -> {}
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

    private fun otherTopHeadLines() =
        getTopNewsUseCase.execute(GetTopNewsUseCase.Param("bbc-news", 2, 3))
            .onEach {
                when (it) {
                    is Resource.Success -> {
                        articlesItemsList = it.data?.articles.orEmpty()
                    }

                    else -> {}
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

}