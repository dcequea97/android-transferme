package com.cequea.transferme.ui.screens.login

enum class LoginScreens {
    Login, SignUp, Profile, PhoneNumber, SecurityQuestions, PinCode
}

enum class LoginPinCodeType {
    Set,
    Existing
}

data class LoginState(
    val activeScreen: LoginScreens = LoginScreens.SignUp,
    val pinCodeType: LoginPinCodeType = LoginPinCodeType.Set
)