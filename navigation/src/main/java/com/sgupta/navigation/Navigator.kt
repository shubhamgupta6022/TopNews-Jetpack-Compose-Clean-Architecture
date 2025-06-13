package com.sgupta.navigation

import androidx.navigation.NavOptionsBuilder
import com.sgupta.core.navigation.NavigationService
import com.sgupta.core.navigation.NewsDestination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() : NavigationService {
    private val _actions = MutableSharedFlow<Action>(
        replay = 0,
        extraBufferCapacity = 10
    )
    val actions: Flow<Action> = _actions.asSharedFlow()

    override fun navigateTo(
        destination: NewsDestination,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        _actions.tryEmit(
            Action.Navigate(destination = destination.createRoute(), navOptions = navOptions)
        )
    }

    override fun goBack() {
        _actions.tryEmit(Action.Back)
    }

    override fun popUpTo(destination: NewsDestination, inclusive: Boolean) {
        _actions.tryEmit(Action.PopUpTo(destination.route, inclusive))
    }

    sealed class Action {
        data class Navigate(
            val destination: String,
            val navOptions: NavOptionsBuilder.() -> Unit
        ) : Action()

        data object Back : Action()
        
        data class PopUpTo(
            val destination: String,
            val inclusive: Boolean
        ) : Action()
    }
}