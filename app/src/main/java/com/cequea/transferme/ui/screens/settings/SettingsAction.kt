package com.cequea.transferme.ui.screens.settings

import com.cequea.transferme.domain.model.SecurityQuestionModel

sealed interface SettingsAction {
    data class OnSecurityQuestionSelected(val question: SecurityQuestionModel) : SettingsAction
    data class AnswerQuestionChanged(val answer: String) : SettingsAction
}