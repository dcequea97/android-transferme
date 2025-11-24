package com.cequea.transferme.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cequea.transferme.ui.components.BottomBar
import com.cequea.transferme.ui.components.SuccessScreen
import com.cequea.transferme.ui.screens.cardDetails.CardDetailsRoot
import com.cequea.transferme.ui.screens.cardDetails.CardDetailsViewModel
import com.cequea.transferme.ui.screens.cardDetails.screens.AddCardRoot
import com.cequea.transferme.ui.screens.home.HomeRoot
import com.cequea.transferme.ui.screens.login.LoginRoot
import com.cequea.transferme.ui.screens.profile.ProfileRoot
import com.cequea.transferme.ui.screens.settings.SettingsRoot
import com.cequea.transferme.ui.screens.settings.SettingsViewModel
import com.cequea.transferme.ui.screens.settings.screens.AnswerQuestionRoot
import com.cequea.transferme.ui.screens.settings.screens.SecurityQuestionsRoot
import com.cequea.transferme.ui.screens.statics.StaticsRoot
import com.cequea.transferme.ui.screens.transfer.TransferRoot
import com.cequea.transferme.ui.screens.wallet.MyWalletRoot
import com.cequea.transferme.ui.screens.welcome.WelcomeRoot
import com.cequea.transferme.ui.theme.TransferMeTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    viewModel: NavigationViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(navBackStackEntry) {
        currentRoute?.let { route ->
            Log.i(null, "Current Route: $route")
            viewModel.showBottomBar(AppScreens.showBottomBar(currentRoute))
            viewModel.setCurrentRoute(route)
        }
    }

    AppNavigationScreen(state, navController)
}

@Composable
private fun AppNavigationScreen(
    state: NavigationState,
    navController: androidx.navigation.NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    TransferMeNavigationDrawer(navController, drawerState) {
        Scaffold(
            topBar = {},
            bottomBar = {},
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(top = 25.dp)
                ) {
                    NavHost(
                        modifier = Modifier.padding(bottom = if (state.showBottomBar) 80.dp else 0.dp),
                        navController = navController,
                        startDestination = AppScreens.HomeScreen
//                    startDestination = AppScreens.WelcomeScreen
                    ) {
                        composable<AppScreens.WelcomeScreen> {
                            WelcomeRoot(navController = navController)
                        }

                        composable<AppScreens.LoginScreen> {
                            LoginRoot(navController = navController)
                        }

                        composable<AppScreens.HomeScreen> {
                            HomeRoot(
                                navController = navController,
                                onNavigationDrawerClick = {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }
                            )
                        }

                        composable<AppScreens.CardDetails> {
                            CardDetailsRoot(navController = navController)
                        }

                        composable<AppScreens.AddCardScreen> { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry(AppScreens.CardDetails)
                            }
                            val viewModel: CardDetailsViewModel =
                                koinViewModel(viewModelStoreOwner = parentEntry)

                            AddCardRoot(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }

                        composable<AppScreens.TransferScreen> {
                            TransferRoot(navController = navController)
                        }

                        composable<AppScreens.WalletScreen> {
                            MyWalletRoot(navController = navController)
                        }

                        composable<AppScreens.ChartScreen> {
                            StaticsRoot(navController = navController)
                        }

                        composable<AppScreens.Settings> {
                            SettingsRoot(navController = navController)
                        }

                        composable<AppScreens.SecurityQuestionsScreen> { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry(AppScreens.ProfileScreen)
                            }
                            val viewModel: SettingsViewModel =
                                koinViewModel(viewModelStoreOwner = parentEntry)

                            SecurityQuestionsRoot(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }

                        composable<AppScreens.AnswerQuestionScreen> { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry(AppScreens.ProfileScreen)
                            }
                            val viewModel: SettingsViewModel =
                                koinViewModel(viewModelStoreOwner = parentEntry)

                            AnswerQuestionRoot(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }

                        composable<AppScreens.ProfileScreen> {
                            ProfileRoot(navController = navController)
                        }

                        composable<AppScreens.SuccessTransactionScreen> { backStackEntry ->
                            val screen =
                                backStackEntry.toRoute<AppScreens.SuccessTransactionScreen>()
                            val message = screen.message

                            SuccessScreen(
                                message = message,
                                onContinue = {
                                    navController.popBackStack(
                                        route = AppScreens.HomeScreen::class.qualifiedName!!,
                                        inclusive = false
                                    )
                                }
                            )
                        }
                    }

                    if (state.showBottomBar) {
                        BottomBar(
                            navController = navController,
                            modifier = Modifier.align(Alignment.BottomCenter),
                            state.currentRoute
                        )
                    }
                }
            }
        )
    }
}

@Composable
@Preview
private fun AppNavigationPreview() {
    TransferMeTheme {
        AppNavigationScreen(
            state = NavigationState(),
            navController = rememberNavController()
        )
    }
}