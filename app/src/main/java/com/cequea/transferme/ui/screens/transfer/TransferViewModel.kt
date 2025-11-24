package com.cequea.transferme.ui.screens.transfer

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class TransferViewModel : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(TransferState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = TransferState()
        )

    fun onAction(action: TransferAction) {
        when (action) {
            is TransferAction.OnAccountNumberChange -> {
                val cleanAccount = action.accountNumber.takeIf { it.isDigitsOnly() }
                _state.update {
                    it.copy(
                        accountNumber = cleanAccount ?: ""
                    )
                }
            }

            is TransferAction.OnAmountChange -> {
                val cleanAmount = action.amount.takeIf { it.isDigitsOnly() }
                _state.update {
                    it.copy(
                        amount = cleanAmount ?: ""
                    )
                }
            }

            is TransferAction.OnBankSelected -> {
                _state.update {
                    it.copy(
                        bankSelected = action.bankName
                    )
                }
            }

            is TransferAction.OnSubmitTransfer -> {
                _state.update { it.copy(step = TransferScreenStep.VERIFICATION) }
            }

            is TransferAction.OnCardSelected -> {
                _state.update { it.copy(cardSelected = action.card) }
            }

            TransferAction.OnBackToInputData -> {
                _state.update { it.copy(step = TransferScreenStep.INPUT_DATA) }
            }

            is TransferAction.OnOtpChanged -> {
                val cleanOtp = action.otp.takeIf { it.isDigitsOnly() }?.take(5)
                _state.update {
                    it.copy(
                        otpNumber = cleanOtp ?: ""
                    )
                }
            }

            TransferAction.OnOtpVerified -> {

            }
        }
    }
}