package com.cequea.transferme.ui.screens.welcome

sealed interface WelcomeAction {
    data object OnContinueClicked: WelcomeAction
}
