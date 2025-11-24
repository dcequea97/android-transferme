package com.cequea.transferme.utils

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun Double.getBalanceFormatted(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return formatter.format(this)
}

fun Double.getBalanceFormatted(currencyCode: String): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
    val currency = try {
        Currency.getInstance(currencyCode)
    } catch (_: IllegalArgumentException) {
        Currency.getInstance(Locale.getDefault())
    }
    formatter.currency = currency
    return formatter.format(this)
}