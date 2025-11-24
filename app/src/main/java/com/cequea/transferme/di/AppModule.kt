package com.cequea.transferme.di

import com.cequea.transferme.ui.navigation.NavigationViewModel
import com.cequea.transferme.ui.screens.cardDetails.CardDetailsViewModel
import com.cequea.transferme.ui.screens.home.HomeViewModel
import com.cequea.transferme.ui.screens.login.LoginViewModel
import com.cequea.transferme.ui.screens.profile.ProfileViewModel
import com.cequea.transferme.ui.screens.settings.SettingsViewModel
import com.cequea.transferme.ui.screens.transfer.TransferViewModel
import com.cequea.transferme.ui.screens.wallet.MyWalletViewModel
import com.cequea.transferme.ui.screens.welcome.WelcomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { WelcomeViewModel() }
    viewModel { LoginViewModel() }
    viewModel { NavigationViewModel() }
    viewModel { HomeViewModel() }
    viewModel { CardDetailsViewModel() }
    viewModel { MyWalletViewModel() }
    viewModel { TransferViewModel() }
    viewModel { ProfileViewModel() }
    viewModel { SettingsViewModel() }
}