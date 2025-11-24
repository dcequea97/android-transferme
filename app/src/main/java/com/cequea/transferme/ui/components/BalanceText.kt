package com.cequea.transferme.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.cequea.transferme.utils.getBalanceFormatted

@Composable
fun BalanceText(
    balance: Double,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.typography.headlineLarge.fontSize,
    letterSpacing: TextUnit = 1.5.sp,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(
        modifier = modifier,
        text = balance.getBalanceFormatted(),
        style = MaterialTheme.typography.headlineLarge.copy(
            fontSize = fontSize,
            color = color,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = letterSpacing
        )
    )
}

@Composable
fun BalanceTextWithLabel(
    label: String,
    balance: Double,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.typography.headlineLarge.fontSize,
    letterSpacing: TextUnit = 1.5.sp
) {
    Column(modifier = modifier) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        BalanceText(balance = balance, letterSpacing = letterSpacing, fontSize = fontSize)
    }
}