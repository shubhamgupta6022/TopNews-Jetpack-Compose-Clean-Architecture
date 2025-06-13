package com.sgupta.composite.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgupta.composite.aiassistant.model.AIAssistantChatUiModel
import com.sgupta.composite.aiassistant.states.AIAssistantBottomSheetViewState
import com.sgupta.composite.home.events.HomeScreenEvents
import com.sgupta.composite.home.model.HomeNewsUiModel
import com.sgupta.composite.home.states.HomeScreenViewState
import com.sgupta.composite.splash.event.SplashScreenEvent
import com.sgupta.composite.splash.state.SplashScreenUIState
import com.sgupta.core.navigation.NavigationService
import com.sgupta.core.network.Resource
import com.sgupta.core.presentation.StateAndEventViewModel
import com.sgupta.core.state.ViewEvent
import com.sgupta.domain.model.ArticleDataModel
import com.sgupta.domain.usecase.GenerateAIAssistantContentUseCase
import com.sgupta.domain.usecase.GetTopNewsUseCase
import com.sgupta.navigation.destinations.Listing
import com.sgupta.navigation.destinations.NewsDetail
import com.sgupta.navigation.destinations.Search
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
    private val getTopNewsUseCase: GetTopNewsUseCase,
    private val generateAIAssistantContentUseCase: GenerateAIAssistantContentUseCase,
    private val navigator: NavigationService
) : StateAndEventViewModel<HomeScreenViewState, HomeScreenEvents>(HomeScreenViewState()) {

    private var aiAssistantChatUiModel = mutableListOf(
        AIAssistantChatUiModel(isUser = false, message = "How can I help you?"),
    )
    var aiAssistantBottomSheetStates by mutableStateOf(
        AIAssistantBottomSheetViewState(
            aiAssistantChatUiModel = aiAssistantChatUiModel
        )
    )
    private var topHeadlinesJob: Job? = null
    private var otherTopHeadlinesJob: Job? = null
    private var topNewsItemsList: List<ArticleDataModel>? = null
    private var articlesItemsList: List<ArticleDataModel>? = null

    init {
        getTopNews()
    }

    private fun getTopNews() {
        topHeadlinesJob?.cancel()
        otherTopHeadlinesJob?.cancel()
        updateUiState { copy(loading = true) }
        topHeadlinesJob = topHeadLinesJob()
        otherTopHeadlinesJob = otherTopHeadLines()
        viewModelScope.launch {
            topHeadlinesJob?.join()
            otherTopHeadlinesJob?.join()
            val newsUiModel = HomeNewsUiModel().copy(
                topNewsItemsList = topNewsItemsList.orEmpty(),
                articlesItemsList = articlesItemsList.orEmpty()
            )
            updateUiState {
                copy(loading = false, newsUiModel = newsUiModel)
            }
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

    override suspend fun handleEvent(event: HomeScreenEvents) {
        when(event) {
            is HomeScreenEvents.GenerateAiContent -> {
                generateAiAssistantContent(event.prompt)
            }
            is HomeScreenEvents.CountriesViewAllClicked -> {
                navigator.navigateTo(
                    Listing(country = event.id)
                )
            }

            is HomeScreenEvents.CategoryFilterClicked -> {
                navigator.navigateTo(
                    Listing(category = event.category)
                )
            }

            is HomeScreenEvents.SearchBarClicked -> {
                navigator.navigateTo(Search)
            }

            is HomeScreenEvents.NewsItemClicked -> {
                navigator.navigateTo(
                    NewsDetail(
                        title = event.title,
                        url = event.url
                    )
                )
            }
        }
    }

    private fun generateAiAssistantContent(prompt: String) {
        aiAssistantChatUiModel = aiAssistantChatUiModel.apply {
            add(
                AIAssistantChatUiModel(
                    isUser = true,
                    message = prompt
                )
            )
        }.toMutableList()
        aiAssistantBottomSheetStates = aiAssistantBottomSheetStates.copy(
            loading = false,
            aiAssistantChatUiModel = aiAssistantChatUiModel
        )
        generateAIAssistantContentUseCase.execute(GenerateAIAssistantContentUseCase.Param(prompt = prompt))
            .onEach {
                when(it) {
                    is Resource.Loading -> {
                        aiAssistantBottomSheetStates = aiAssistantBottomSheetStates.copy(loading = true)
                    }

                    is Resource.Error -> {
                        aiAssistantBottomSheetStates = aiAssistantBottomSheetStates.copy(loading = false, error = it.error)
                    }

                    is Resource.Success -> {
                        aiAssistantChatUiModel = aiAssistantChatUiModel.apply {
                            add(
                                AIAssistantChatUiModel(
                                    isUser = false,
                                    message = it.data?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text.orEmpty()
                                )
                            )
                        }.toMutableList()
                        aiAssistantBottomSheetStates = aiAssistantBottomSheetStates.copy(
                            loading = false,
                            aiAssistantChatUiModel = aiAssistantChatUiModel
                        )
                    }
                    else -> {

                    }
                }
            }
            .launchIn(viewModelScope)
    }
}