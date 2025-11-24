package com.cequea.transferme.utils

import java.time.LocalDateTime

fun LocalDateTime.toFormattedString(): String {
    val day = this.dayOfMonth.toString().padStart(2, '0')
    val month = this.monthValue.toString().padStart(2, '0')
    val year = this.year.toString()
    return "$day/$month/$year"
}