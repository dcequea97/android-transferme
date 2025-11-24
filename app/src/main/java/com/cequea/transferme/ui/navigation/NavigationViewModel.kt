package com.cequea.transferme.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class NavigationViewModel : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(NavigationState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = NavigationState()
        )

    fun showBottomBar(show: Boolean) {
        _state.update { it.copy(showBottomBar = show) }
    }

    fun setCurrentRoute(route: String) {
        _state.update { it.copy(currentRoute = route) }
    }
}