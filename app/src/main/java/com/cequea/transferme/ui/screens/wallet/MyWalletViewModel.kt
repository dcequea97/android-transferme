package com.cequea.transferme.ui.screens.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class MyWalletViewModel: ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MyWalletState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MyWalletState()
        )

    fun onAction(action: MyWalletAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }
}