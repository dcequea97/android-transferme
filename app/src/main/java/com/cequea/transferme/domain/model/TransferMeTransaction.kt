package com.cequea.transferme.domain.model

import java.time.LocalDateTime

data class TransferMeTransaction(
    val id: Int,
    val amount: Double,
    val direction: TransactionDirection,
    val date: LocalDateTime,
    val description: String,
    val receiverName: String
)

enum class TransactionDirection {
    DEBIT, CREDIT
}