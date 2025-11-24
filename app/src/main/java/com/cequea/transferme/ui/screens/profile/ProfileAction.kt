package com.cequea.transferme.ui.screens.profile

sealed interface ProfileAction {
    data object ChangePin: ProfileAction
    data object ChangePassword: ProfileAction
    data object EnableFingerprint: ProfileAction
}