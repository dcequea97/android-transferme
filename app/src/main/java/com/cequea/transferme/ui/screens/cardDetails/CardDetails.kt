package com.cequea.transferme.ui.screens.cardDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cequea.transferme.R
import com.cequea.transferme.domain.model.Currency
import com.cequea.transferme.ui.components.CardItem
import com.cequea.transferme.ui.components.TransactionItem
import com.cequea.transferme.ui.components.TransferMeButton
import com.cequea.transferme.ui.components.TransferMeCustomList
import com.cequea.transferme.ui.components.TransferMeSimpleHeader
import com.cequea.transferme.ui.theme.TransferMeTheme
import com.cequea.transferme.utils.Constants
import com.cequea.transferme.utils.getBalanceFormatted
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun CardDetailsRoot(
    navController: NavController,
    viewModel: CardDetailsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.onNavigate.collectLatest { screen ->
            navController.navigate(screen)
        }
    }

    CardDetailsScreen(
        state = state,
        onAction = viewModel::onAction,
        navController = navController
    )
}

@Composable
fun CardDetailsScreen(
    state: CardDetailsUIState,
    onAction: (CardDetailsAction) -> Unit,
    navController: NavController
) {
    val horizontalPadding = Constants.HORIZONTAL_PADDING.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TransferMeSimpleHeader(
            modifier = Modifier.padding(horizontalPadding),
            title = "Card Details",
            onBackClick = { navController.popBackStack() }
        )

        LazyRow(
            modifier = Modifier.padding(start = horizontalPadding)
        ) {
            items(state.cards) { card ->
                CardItem(card = card, modifier = Modifier.padding(end = 8.dp))
            }
        }

        val amountByCurrency = state.cards.groupBy { it.currency }.mapValues { entry ->
            entry.value.sumOf { it.balance }
        }
        CurrencySelector(
            currencies = state.availableCurrencies,
            amountByCurrency = amountByCurrency,
            selectedCurrency = state.selectedCurrency,
            onCurrencySelected = { currency ->
                onAction(CardDetailsAction.CurrencySelected(currency))
            },
            modifier = Modifier
                .padding(
                    top = 24.dp
                )
        )

        TransferMeButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp),
            text = "Add Card",
            onClick = { onAction(CardDetailsAction.AddCardClicked) },
        )

        Spacer(Modifier.height(16.dp))
        TransferMeCustomList(
            modifier = Modifier.padding(horizontal = Constants.HORIZONTAL_PADDING.dp),
            title = "Cash Backs",
            onSeeAllClicked = { /* TODO */ }
        ) {
            LazyColumn(
                modifier = Modifier.heightIn(max = 1000.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(state.lastTransactions) { index, transaction ->
                    val isLast = index == state.lastTransactions.lastIndex
                    TransactionItem(
                        transaction = transaction,
                        modifier = if (isLast) {
                            Modifier.padding(bottom = 12.dp)
                        } else {
                            Modifier
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CurrencySelector(
    currencies: List<Currency>,
    amountByCurrency: Map<Currency, Double>,
    selectedCurrency: Currency,
    onCurrencySelected: (Currency) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Constants.HORIZONTAL_PADDING.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(0XFFF7F5F5),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                currencies.forEach { currency ->
                    CurrencyItem(
                        currency = currency,
                        amount = amountByCurrency[currency],
                        isSelected = currency == selectedCurrency,
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                onClick = { onCurrencySelected(currency) },
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun CurrencyItem(
    currency: Currency,
    amount: Double?,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        Color(0XFF5164BF)
    } else {
        Color(0XFF8EDFEB)
    }

    Box(
        modifier = modifier
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = currency.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                )

                Icon(
                    painter = painterResource(R.drawable.arrow_up_icon),
                    contentDescription = "Arrow",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(top = 4.dp, end = 4.dp)
                        .size(10.dp)
                        .rotate(
                            if (isSelected) {
                                180f
                            } else {
                                0f
                            }
                        )
                )
            }

            Text(
                text = amount?.getBalanceFormatted(currency.name) ?: "0.0",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CardDetailsScreenPreview() {
    TransferMeTheme {
        CardDetailsScreen(
            state = CardDetailsUIState(),
            onAction = {},
            navController = rememberNavController()
        )
    }
}