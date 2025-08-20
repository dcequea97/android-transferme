package com.cequea.transferme.di

import com.cequea.transferme.ui.screens.welcome.WelcomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        WelcomeViewModel()
    }
}