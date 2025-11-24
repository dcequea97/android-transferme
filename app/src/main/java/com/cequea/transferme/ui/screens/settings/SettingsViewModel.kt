package com.cequea.transferme.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.transferme.domain.model.SecurityQuestionModel
import com.cequea.transferme.ui.navigation.AppScreens
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = SettingsState()
        )

    private val _onQuestionSelected = Channel<SecurityQuestionModel>()
    val onQuestionSelected = _onQuestionSelected.receiveAsFlow()

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnSecurityQuestionSelected -> {
                _state.update { it.copy(activeQuestion = action.question) }
                _onQuestionSelected.trySend(action.question)
            }

            is SettingsAction.AnswerQuestionChanged -> {
                _state.update {
                    it.copy(
                        questions = it.questions.map { question ->
                            if (question.question == it.activeQuestion?.question) {
                                question.copy(answer = action.answer)
                            } else {
                                question
                            }
                        },
                        activeQuestion = it.activeQuestion?.copy(answer = action.answer)
                    )
                }
            }
        }
    }
}