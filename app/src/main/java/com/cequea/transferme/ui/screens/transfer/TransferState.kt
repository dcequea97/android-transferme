package com.cequea.transferme.ui.screens.transfer

import com.cequea.transferme.domain.model.Card

data class TransferState(
    val isLoading: Boolean = false,
    val step: TransferScreenStep = TransferScreenStep.INPUT_DATA,
    val availableBalance: Double = 213423.83,
    val banksList: List<String> = listOf(
        "MCB",
        "Afalah",
        "Santander",
        "Falabella",
        "Scotiabank",
        "Itau",
        "Banco de Chile",
        "Banco Estado",
        "Banco Bice",
        "Banco Ripley",
        "Banco Security",
        "Banesco",
        "Banco del Desarrollo",
    ),
    val accountNumber: String = "",
    val amount: String = "",
    val bankSelected: String = "",
    val cardSelected: Card? = null,
    val cards: List<Card> = listOf(
        Card(
            cardNumber = "1234563827361234",
            cardHolderName = "Juan Perez",
            expirationDate = "12/24",
            cardType = com.cequea.transferme.domain.model.CardType.VISA,
            balance = 1500000.75,
            currency = com.cequea.transferme.domain.model.Currency.USD
        ),
        Card(
            cardNumber = "9876543210123456",
            cardHolderName = "Maria Gomez",
            expirationDate = "11/25",
            cardType = com.cequea.transferme.domain.model.CardType.MASTERCARD,
            balance = 1353943.57,
            currency = com.cequea.transferme.domain.model.Currency.USD
        )
    ),
    val otpNumber: String = "",
)
