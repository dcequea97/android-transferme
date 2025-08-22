package com.cequea.transferme.ui.screens.login

sealed interface LoginAction {
    data class OnLoginScreenChanged(val screen: LoginScreens): LoginAction
    data class SetPinCodeTextHeader(val pinCode: LoginPinCodeType): LoginAction
}
