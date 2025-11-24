package com.cequea.transferme.ui.screens.statics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class StaticsViewModel: ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(StaticsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = StaticsState()
        )

    fun onAction(action: StaticsAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }
}