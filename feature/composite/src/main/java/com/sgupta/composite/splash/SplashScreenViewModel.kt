package com.sgupta.composite.splash

import com.sgupta.composite.splash.event.SplashScreenEvent
import com.sgupta.composite.splash.state.SplashScreenUIState
import com.sgupta.core.navigation.NavigationService
import com.sgupta.core.presentation.StateAndEventViewModel
import com.sgupta.navigation.destinations.Home
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val navigator: NavigationService
) :
    StateAndEventViewModel<SplashScreenUIState, SplashScreenEvent>(SplashScreenUIState()) {
    override suspend fun handleEvent(event: SplashScreenEvent) {
        when(event) {
            is SplashScreenEvent.Loaded -> {
                // Handle the loaded event, e.g., navigate to the next screen
                updateUiState { copy(isLoading = false) }
                navigator.popUpTo(Home, true)
            }
        }
    }
}