package com.cequea.transferme.domain.model

data class Card(
    val cardNumber: String,
    val cardHolderName: String,
    val expirationDate: String,
    val cardType: CardType,
    val currency: Currency,
    val balance: Double
)

enum class CardType(val displayText: String) {
    VISA("Visa"),
    MASTERCARD("MasterCard"),
}