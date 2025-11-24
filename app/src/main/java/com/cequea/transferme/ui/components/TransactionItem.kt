package com.cequea.transferme.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cequea.transferme.R
import com.cequea.transferme.domain.model.TransactionDirection
import com.cequea.transferme.domain.model.TransferMeTransaction
import com.cequea.transferme.utils.getBalanceFormatted
import com.cequea.transferme.utils.toFormattedString

@Composable
fun TransactionItem(
    transaction: TransferMeTransaction,
    modifier: Modifier = Modifier,
    onCLick: (TransferMeTransaction) -> Unit = {}
) {
    val isDebit = transaction.direction == TransactionDirection.DEBIT
    val amountColor = if (isDebit) {
        Color(0XFF5164BF)
    } else {
        Color(0XFF8EDFEB)
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable{ onCLick(transaction) },
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        color = Color.White,
        shape = RoundedCornerShape(20.dp),
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "User",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray.copy(0.5f), CircleShape)
                        .padding(5.dp),
                    tint = Color.Gray
                )

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                ) {
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = transaction.description,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = transaction.date.toFormattedString()
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_up_icon),
                    contentDescription = "Arrow",
                    tint = amountColor,
                    modifier = Modifier
                        .padding(top = 4.dp, end = 4.dp)
                        .size(14.dp)
                        .rotate(
                            if (isDebit) {
                                180f
                            } else {
                                0f
                            }
                        )
                )

                Text(
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = amountColor,
                    text = transaction.amount.getBalanceFormatted()
                )
            }
        }

    }
}

@Composable
@Preview
private fun TransactionItemPreview() {
    val transaction = TransferMeTransaction(
        id = 1,
        amount = -25000.0,
        direction = TransactionDirection.DEBIT,
        date = java.time.LocalDateTime.now().minusDays(1),
        description = "Pago de servicios",
        receiverName = "Empresa XYZ"
    )

    TransactionItem(transaction = transaction)
}