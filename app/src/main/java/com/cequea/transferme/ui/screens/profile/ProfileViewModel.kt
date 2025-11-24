package com.cequea.transferme.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.transferme.ui.navigation.AppScreens
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class ProfileViewModel : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ProfileState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ProfileState()
        )

    private val _onNavigate = Channel<AppScreens>()
    val onNavigate = _onNavigate.receiveAsFlow()

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.ChangePassword -> _onNavigate.trySend(AppScreens.SecurityQuestionsScreen)
            ProfileAction.ChangePin -> _onNavigate.trySend(AppScreens.SecurityQuestionsScreen)
            ProfileAction.EnableFingerprint -> {}
        }
    }
}