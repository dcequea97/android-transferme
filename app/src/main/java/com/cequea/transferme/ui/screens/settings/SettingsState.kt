package com.cequea.transferme.ui.screens.settings

import com.cequea.transferme.domain.model.SecurityQuestionModel

data class SettingsState(
    val isLoading: Boolean = false,
    val questions: List<SecurityQuestionModel> = listOf(
        SecurityQuestionModel("What was your First School’s name?", null),
        SecurityQuestionModel("What is your favorite food?", null),
        SecurityQuestionModel("What city were you born in?", null),
        SecurityQuestionModel("What is your pet’s name?", null),
        SecurityQuestionModel("What is your mother’s maiden name?", null),
    ),
    val activeQuestion: SecurityQuestionModel? = null
)
