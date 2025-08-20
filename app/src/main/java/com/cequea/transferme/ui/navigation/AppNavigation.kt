package com.cequea.transferme.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cequea.transferme.ui.screens.welcome.WelcomeRoot

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {},
        bottomBar = {},
        content = { paddingValues ->
            NavHost(
                modifier = Modifier
                    .padding(paddingValues),
                navController = navController,
                startDestination = AppScreens.WelcomeScreen
            ) {
                composable<AppScreens.WelcomeScreen> {
                    WelcomeRoot(navController = navController)
                }
            }
        }
    )
}