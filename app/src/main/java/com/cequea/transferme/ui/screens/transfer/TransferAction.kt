package com.cequea.transferme.ui.screens.transfer

import com.cequea.transferme.domain.model.Card

sealed interface TransferAction {
    data class OnAccountNumberChange(val accountNumber: String) : TransferAction
    data class OnAmountChange(val amount: String) : TransferAction
    data class OnBankSelected(val bankName: String) : TransferAction
    data class OnCardSelected(val card: Card) : TransferAction
    data class OnOtpChanged(val otp: String) : TransferAction
    data object OnSubmitTransfer: TransferAction
    data object OnBackToInputData: TransferAction
    data object OnOtpVerified: TransferAction
}