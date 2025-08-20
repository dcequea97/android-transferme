package com.cequea.transferme.ui.navigation

import kotlinx.serialization.Serializable

sealed class AppScreens : Screen {
    @Serializable
    data object WelcomeScreen: AppScreens()

    @Serializable
    data object LoginScreen: AppScreens()
}