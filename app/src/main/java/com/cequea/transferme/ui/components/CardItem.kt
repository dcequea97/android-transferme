package com.cequea.transferme.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cequea.transferme.R
import com.cequea.transferme.domain.model.Card
import com.cequea.transferme.domain.model.CardType
import com.cequea.transferme.domain.model.Currency
import com.cequea.transferme.ui.theme.TransferMeTheme
import kotlin.random.Random
import kotlin.text.chunked

enum class CardMode {
    SIMPLE,
    DETAILED
}

@Composable
fun CardItem(
    card: Card,
    modifier: Modifier = Modifier,
    mode: CardMode = CardMode.SIMPLE,
    backgroundColor: Color? = null
) {
    when (mode) {
        CardMode.SIMPLE -> CardItemSimple(card = card, modifier = modifier)
        CardMode.DETAILED -> CardItemDetailed(
            card = card,
            modifier = modifier,
            backgroundColor = backgroundColor
        )
    }

}

@Composable
private fun CardItemDetailed(
    card: Card,
    modifier: Modifier = Modifier,
    backgroundColor: Color? = null
) {
    val cardColors = if (backgroundColor == null) listOf(
        Color(0XFF5366BE),
        Color(0XFF8EDFEB),
        Color(0XFFF59D31),
    ) else listOf(backgroundColor)
    val color = cardColors[Random.nextInt(cardColors.size)]
    val textColor = Color.White
    Box(
        modifier = modifier
            .width(340.dp)
            .height(200.dp)
            .background(color, RoundedCornerShape(22.dp))
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = 1.25f,
                    scaleY = 1.25f,
                    transformOrigin = TransformOrigin.Center
                ),
            painter = painterResource(R.drawable.detailed_card_background),
            contentDescription = "Card Chip",
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
                            .weight(0.5f),
                        text = card.cardHolderName,
                        style = MaterialTheme.typography.titleSmall,
                        color = textColor
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
                            .weight(0.5f),
                        text = "A Debit Card",
                        style = MaterialTheme.typography.titleSmall.copy(textAlign = TextAlign.End),
                        color = textColor
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, top = 4.dp, bottom = 4.dp),
                        text = card.cardNumber.chunked(4).joinToString(" "),
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, top = 4.dp, bottom = 4.dp),
                        text = card.expirationDate,
                        style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End),
                        color = textColor
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "Your Balance",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(0.8f)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BalanceText(
                        balance = card.balance,
                        fontSize = 22.sp,
                        letterSpacing = 0.5.sp,
                        color = Color.White
                    )

                    Text(
                        text = card.cardType.displayText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .padding(bottom = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CardItemSimple(card: Card, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(340.dp)
            .height(100.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(22.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(0.2f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(R.drawable.visa),
                    contentDescription = "Card Logo",
                )
            }
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
                    .weight(0.5f),
                text = "${card.cardType.displayText}  ●  ${card.cardNumber.takeLast(4)}",
                style = MaterialTheme.typography.titleMedium.copy(

                )
            )

            BalanceText(
                modifier = Modifier.weight(0.3f),
                balance = card.balance,
                fontSize = 12.sp,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CardItemSimplePreview() {
    val sampleCard = Card(
        cardNumber = "1234563827361234",
        cardHolderName = "Juan Perez",
        expirationDate = "12/24",
        cardType = CardType.VISA,
        balance = 1500000.75,
        currency = Currency.USD
    )
    TransferMeTheme {
        CardItemSimple(card = sampleCard)
    }
}

@Composable
@Preview(showBackground = true)
private fun CardItemDetailedPreview() {
    val sampleCard = Card(
        cardNumber = "1234563827361234",
        cardHolderName = "Juan Perez",
        expirationDate = "12/24",
        cardType = CardType.VISA,
        balance = 1500000.75,
        currency = Currency.USD
    )
    TransferMeTheme {
        CardItemDetailed(card = sampleCard)
    }
}