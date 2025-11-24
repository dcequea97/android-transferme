package com.cequea.transferme.ui.screens.wallet

import com.cequea.transferme.domain.model.Card
import com.cequea.transferme.domain.model.Category
import com.cequea.transferme.domain.model.getAllCategories

data class MyWalletState(
    val isLoading: Boolean = false,
    val availableBalance: Double = 213423.83,
    val categoriesAvailable: List<Category> = getAllCategories(),
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
    )
)
