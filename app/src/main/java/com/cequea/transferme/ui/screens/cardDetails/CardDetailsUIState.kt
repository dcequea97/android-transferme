package com.cequea.transferme.ui.screens.cardDetails

import com.cequea.transferme.domain.model.Card
import com.cequea.transferme.domain.model.CardType
import com.cequea.transferme.domain.model.Currency
import com.cequea.transferme.domain.model.TransactionDirection
import com.cequea.transferme.domain.model.TransferMeTransaction
import com.cequea.transferme.ui.screens.cardDetails.screens.AddCardStep

data class CardDetailsUIState(
    val isLoading: Boolean = false,
    val selectedCurrency: Currency = Currency.USD,
    val availableCurrencies: List<Currency> = listOf(
        Currency.USD,
        Currency.EUR,
        Currency.YEN
    ),
    val selectedCard: Card? = null,
    val lastTransactions: List<TransferMeTransaction> = listOf(
        TransferMeTransaction(
            id = 1,
            amount = 25000.0,
            direction = TransactionDirection.DEBIT,
            date = java.time.LocalDateTime.now().minusDays(1),
            description = "Pago de servicios",
            receiverName = "Empresa XYZ"
        ),
        TransferMeTransaction(
            id = 2,
            amount = 50000.0,
            direction = TransactionDirection.CREDIT,
            date = java.time.LocalDateTime.now().minusDays(2),
            description = "Transferencia recibida",
            receiverName = "Amigo Juan"
        ),
        TransferMeTransaction(
            id = 3,
            amount = 15000.0,
            direction = TransactionDirection.DEBIT,
            date = java.time.LocalDateTime.now().minusDays(3),
            description = "Compra en supermercado",
            receiverName = "Supermercado ABC",
        ),
        TransferMeTransaction(
            id = 4,
            amount = 15000.0,
            direction = TransactionDirection.DEBIT,
            date = java.time.LocalDateTime.now().minusDays(3),
            description = "Compra en supermercado parte 2",
            receiverName = "Supermercado ABC",
        )
    ),
    val cards: List<Card> = listOf(
        Card(
            cardNumber = "1234563827361234",
            cardHolderName = "Juan Perez",
            expirationDate = "12/24",
            cardType = CardType.VISA,
            balance = 1500000.75,
            currency = Currency.USD
        ),
        Card(
            cardNumber = "9876543210123456",
            cardHolderName = "Maria Gomez",
            expirationDate = "11/25",
            cardType = CardType.MASTERCARD,
            balance = 1353943.57,
            currency = Currency.USD
        ),
        Card(
            cardNumber = "4567891234567890",
            cardHolderName = "Carlos Ruiz",
            expirationDate = "10/23",
            cardType = CardType.VISA,
            balance = 100000.00,
            currency = Currency.YEN
        )
    ),
    val newCardData: Card = Card(
        cardNumber = "",
        cardHolderName = "",
        expirationDate = "",
        cardType = CardType.VISA,
        balance = 0.0,
        currency = Currency.USD
    ),
    val addCardStep: AddCardStep = AddCardStep.FORM,
)
