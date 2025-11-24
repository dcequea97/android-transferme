package com.cequea.transferme.ui.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cequea.transferme.R
import com.cequea.transferme.domain.model.TransactionDirection
import com.cequea.transferme.domain.model.TransferMeTransaction
import com.cequea.transferme.ui.components.BalanceTextWithLabel
import com.cequea.transferme.ui.components.CardItem
import com.cequea.transferme.ui.components.TransferMeCustomList
import com.cequea.transferme.ui.theme.TransferMeTheme
import com.cequea.transferme.utils.Constants
import com.cequea.transferme.utils.getBalanceFormatted
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@Composable
fun HomeRoot(
    navController: NavController,
    onNavigationDrawerClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = viewModel::onAction,
        onNavigationDrawerClick = onNavigationDrawerClick
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    onNavigationDrawerClick: () -> Unit
) {
    val horizontalPadding = Constants.HORIZONTAL_PADDING.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = horizontalPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = horizontalPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigationDrawerClick
            ) {
                Icon(
                    tint = Color.Black,
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = "Menu icon",
                    modifier = Modifier
                        .size(20.dp)
                )
            }

            IconButton(
                onClick = { }
            ) {
                Icon(
                    tint = Color.Black,
                    painter = painterResource(id = R.drawable.bell),
                    contentDescription = "Notifications icon",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        BalanceTextWithLabel(
            "Current Balance", state.currBalance
        )
        Spacer(modifier = Modifier.height(32.dp))
        LazyRow {
            items(state.cards) { card ->
                CardItem(card = card, modifier = Modifier.padding(end = 8.dp))
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        TransferMeCustomList(
            title = "Incoming Transactions",
            onSeeAllClicked = { /* TODO */ }
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.transactions.filter { it.direction == TransactionDirection.CREDIT }) { transaction ->
                    TransactionResumeCard(
                        transaction = transaction,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
//                                onAction(HomeAction.OnTransactionClick(transaction))
                            }
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        TransferMeCustomList(
            title = "Outgoing Transactions",
            onSeeAllClicked = { /* TODO */ }
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.transactions.filter { it.direction == TransactionDirection.DEBIT }) { transaction ->
                    TransactionResumeCard(
                        transaction = transaction,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
//                                onAction(HomeAction.OnTransactionClick(transaction))
                            }
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}


@Composable
private fun TransactionResumeCard(
    transaction: TransferMeTransaction,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(20.dp)
    val symbol = when (transaction.direction) {
        TransactionDirection.DEBIT -> "-"
        TransactionDirection.CREDIT -> "+"
    }
    val color = when (transaction.direction) {
        TransactionDirection.DEBIT -> Color(0XFF5164BF)
        TransactionDirection.CREDIT -> Color(0XFF8EDFEB)
    }
    Surface(
        modifier = modifier
            .height(220.dp)
            .width(160.dp),
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        color = Color.White
    ) {
        BottomWave(
            waveColor = color,
//            modifier = Modifier.align(Alignment.BottomCenter)
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User",
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray.copy(0.5f), CircleShape)
                        .padding(5.dp),
                    tint = Color.Gray
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.padding(end = 8.dp, top = 8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_up_icon),
                        contentDescription = "Arrow",
                        tint = color,
                        modifier = Modifier
                            .size(14.dp)
                            .rotate(
                                when (transaction.direction) {
                                    TransactionDirection.DEBIT -> 0f
                                    TransactionDirection.CREDIT -> 180f
                                }
                            )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = symbol + transaction.amount.getBalanceFormatted(),
                        color = color,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "From",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light)
                )

                Text(
                    text = transaction.receiverName.replace(" ", "\n"),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            // Date with format "dd allMonthName yyyy" ej: 23 December 2024
//            SHould be at the bottom

            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "${transaction.date.dayOfMonth} ${
                        transaction.date.month.name.lowercase().replaceFirstChar { it.uppercase() }
                    } ${transaction.date.year}",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomStart)
                )
            }
        }
    }
}

@Composable
fun BottomWave(
    modifier: Modifier = Modifier,
    waveColor: Color = Color(0xFFBEE3F8),
    waveHeight: Float = 140f
) {
    val pattern = remember { Random.nextInt(1, 4) }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        val w = size.width
        val h = size.height
        val baseY = h - waveHeight

        val path = Path().apply {
            moveTo(0f, baseY)

            when (pattern) {
                1 -> {
                    quadraticTo(w * 0.25f, baseY - waveHeight, w * 0.5f, baseY)
                    quadraticTo(w * 0.75f, baseY + waveHeight, w, baseY - waveHeight)
                }

                2 -> {
                    quadraticTo(w * 0.16f, baseY - waveHeight, w * 0.33f, baseY)
                    quadraticTo(w * 0.5f, baseY + waveHeight, w * 0.66f, baseY)
                    quadraticTo(w * 0.83f, baseY - waveHeight, w, baseY)
                }

                3 -> {
                    quadraticTo(w * 0.5f, baseY + waveHeight * 1.2f, w, baseY - waveHeight)
                }
            }

            lineTo(w, h)
            lineTo(0f, h)
            close()
        }

        // Degradado de abajo (waveColor) hacia arriba (blanco)
        val gradient = Brush.verticalGradient(
            colors = listOf(Color.White, waveColor),
            startY = h,
            endY = 0f
        )

        drawPath(path = path, brush = gradient)
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeScreenPreview() {
    TransferMeTheme {
        HomeScreen(
            state = HomeState(
                currBalance = 1234.56
            ),
            onAction = {},
            onNavigationDrawerClick = {}
        )
    }
}