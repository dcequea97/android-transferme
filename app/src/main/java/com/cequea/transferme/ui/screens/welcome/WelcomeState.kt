package com.cequea.transferme.ui.screens.welcome

data class WelcomeItem(
    val resourceId: Int,
    val title: String,
    val body: String
)

data class WelcomeState(
    val activeScreen: Int = 1,
    val items: List<WelcomeItem> = listOf(),
    val paramTwo: List<String> = emptyList(),
)