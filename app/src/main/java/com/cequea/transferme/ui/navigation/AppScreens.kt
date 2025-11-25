package com.cequea.transferme.ui.navigation

import kotlinx.serialization.Serializable

sealed class AppScreens : Screen {
    @Serializable
    data object WelcomeScreen: AppScreens()

    @Serializable
    data object LoginScreen: AppScreens()

    @Serializable
    data object HomeScreen: AppScreens()

    @Serializable
    data object CardDetails: AppScreens()

    @Serializable
    data object WalletScreen: AppScreens()

    @Serializable
    data object ProfileScreen: AppScreens()

    @Serializable
    data object TransferScreen: AppScreens()

    @Serializable
    data class SuccessTransactionScreen(val message: String): AppScreens()

    @Serializable
    data object  ChartScreen: AppScreens()

    @Serializable
    data object Settings: AppScreens()

    @Serializable
    data object SecurityQuestionsScreen: AppScreens()

    @Serializable
    data object AnswerQuestionScreen: AppScreens()

    @Serializable
    data object AddCardScreen: AppScreens()

    @Serializable
    data object StaticsScreen: AppScreens()

    companion object {
        fun showBottomBar(currentRoute: String): Boolean {
            fun routeOf(screen: AppScreens): String = screen::class.qualifiedName ?: ""

            val showScreens = listOf(
                routeOf(HomeScreen),
                routeOf(WalletScreen),
                routeOf(ProfileScreen),
                routeOf(ChartScreen),
                routeOf(CardDetails),
                routeOf(StaticsScreen),
            )
            return showScreens.contains(currentRoute)
        }

        fun enableDrawerGestures(currentRoute: String): Boolean {
            fun routeOf(screen: AppScreens): String = screen::class.qualifiedName ?: ""

            val hideScreens = listOf(
                routeOf(WelcomeScreen),
                routeOf(LoginScreen),
            )
            return !hideScreens.contains(currentRoute)
        }
    }
}