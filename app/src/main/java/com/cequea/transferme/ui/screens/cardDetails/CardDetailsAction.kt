package com.cequea.transferme.ui.screens.cardDetails

import com.cequea.transferme.domain.model.Currency

sealed interface CardDetailsAction {
    data class CurrencySelected(val currency: Currency) : CardDetailsAction
    data object AddCardClicked: CardDetailsAction
    data object OnConfirmAddCardFormClicked: CardDetailsAction
    data class OnNewCardNumberChange(val newData: String) : CardDetailsAction
    data class OnNewCardHolderNameChange(val newData: String) : CardDetailsAction
    data class OnNewCardExpiryDateChange(val newData: String) : CardDetailsAction
}