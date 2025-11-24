package com.cequea.transferme.ui.screens.transfer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cequea.transferme.ui.components.BalanceTextWithLabel
import com.cequea.transferme.ui.components.CardItem
import com.cequea.transferme.ui.components.CardMode
import com.cequea.transferme.ui.components.TransferMeButton
import com.cequea.transferme.ui.components.TransferMeSimpleHeader
import com.cequea.transferme.ui.components.UnderlinedTextField
import com.cequea.transferme.ui.theme.TransferMeTheme
import com.cequea.transferme.utils.Constants
import com.cequea.transferme.utils.visualTransfomations.AccountVisualTransformation
import com.cequea.transferme.utils.visualTransfomations.AmountVisualTransformation
import org.koin.androidx.compose.koinViewModel

enum class TransferScreenStep {
    INPUT_DATA,
    VERIFICATION
}

@Composable
fun TransferRoot(
    navController: NavController,
    viewModel: TransferViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TransferScreen(
        state = state,
        onAction = viewModel::onAction,
        navController = navController
    )
}

@Composable
fun TransferScreen(
    state: TransferState,
    onAction: (TransferAction) -> Unit,
    navController: NavController
) {
    AnimatedContent(
        targetState = state.step,
        transitionSpec = {
            // Animación de entrada/salida
            (slideInHorizontally { it } + fadeIn()).togetherWith(
                slideOutHorizontally { -it } + fadeOut()
            )
        }
    ) { step ->
        when (step) {
            TransferScreenStep.INPUT_DATA -> {
                InputDataScreen(
                    state = state,
                    onAction = onAction,
                    navController = navController
                )
            }

            TransferScreenStep.VERIFICATION -> {
                VerificationScreen(
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun VerificationScreen(
    state: TransferState,
    onAction: (TransferAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Constants.HORIZONTAL_PADDING.dp)
    ) {
        TransferMeSimpleHeader(
            modifier = Modifier.padding(vertical = Constants.HORIZONTAL_PADDING.dp),
            title = "Verify your Number",
            onBackClick = { onAction(TransferAction.OnBackToInputData) }
        )

        Spacer(Modifier.height(24.dp))

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Please verify your\nPhone Number",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Light
        )

        Spacer(Modifier.height(24.dp))

        UnderlinedTextField(
            label = "Enter Verification Code (5-digit)",
            value = state.otpNumber,
            onValueChange = {
                onAction(TransferAction.OnOtpChanged(it))
            },
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        )

        Spacer(Modifier.height(32.dp))

        TransferMeButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Confirm Transfer",
            onClick = { onAction(TransferAction.OnOtpVerified) },
        )
    }
}

@Composable
private fun InputDataScreen(
    state: TransferState,
    onAction: (TransferAction) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Constants.HORIZONTAL_PADDING.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TransferMeSimpleHeader(
            modifier = Modifier.padding(vertical = Constants.HORIZONTAL_PADDING.dp),
            title = "Money Transfer",
            onBackClick = { navController.popBackStack() }
        )

        BalanceTextWithLabel(
            "Current Balance", state.availableBalance,
        )

        Spacer(Modifier.height(12.dp))

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Please, enter the receiver’s bank account number in below field.",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Light
        )
        UnderlinedTextField(
            label = "Account No.",
            value = state.accountNumber,
            onValueChange = {
                onAction(TransferAction.OnAccountNumberChange(it))
            },
            visualTransformation = AccountVisualTransformation(' '),
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        )
        Spacer(Modifier.height(22.dp))
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Please, enter the amount of money transfer in below field.",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Light
        )
        UnderlinedTextField(
            label = "Enter Amount.",
            value = state.amount,
            onValueChange = {
                onAction(TransferAction.OnAmountChange(it))
            },
            visualTransformation = AmountVisualTransformation(),
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        )
        Spacer(Modifier.height(22.dp))
        var isBanksDropdownExpanded by remember { mutableStateOf(true) }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .weight(0.8f),
                text = "Please, select a bank from which you want to do the money transfer.",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Light
            )
            IconButton(
                modifier = Modifier.weight(0.2f),
                onClick = { isBanksDropdownExpanded = !isBanksDropdownExpanded }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    Modifier.rotate(if (isBanksDropdownExpanded) 180f else 0f)
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        AnimatedVisibility(
            modifier = Modifier.heightIn(max = 500.dp),
            visible = isBanksDropdownExpanded
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(state.banksList) { bankName ->
                    Card(
                        colors = if (state.bankSelected == bankName) {
                            CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = {
                            onAction(TransferAction.OnBankSelected(bankName))
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxSize()
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = bankName,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }

        var isCardsDropdownExpanded by remember { mutableStateOf(true) }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .weight(0.8f),
                text = "Please, select a card you are willing to do the money transfer with.",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Light
            )

            IconButton(
                modifier = Modifier.weight(0.2f),
                onClick = { isCardsDropdownExpanded = !isCardsDropdownExpanded }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    Modifier.rotate(if (isCardsDropdownExpanded) 180f else 0f)
                )
            }
        }

        Spacer(Modifier.height(12.dp))
        AnimatedVisibility(
            modifier = Modifier.heightIn(max = 500.dp),
            visible = isCardsDropdownExpanded
        ) {
            LazyColumn {
                items(state.cards) { card ->
                    CardItem(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .clickable(
                                onClick = {
                                    onAction(TransferAction.OnCardSelected(card))
                                }
                            ),
                        card = card,
                        mode = CardMode.DETAILED
                    )
                }
            }
        }

        TransferMeButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp),
            text = "Next",
            onClick = { onAction(TransferAction.OnSubmitTransfer) },
        )
    }
}

@Composable
@Preview(showBackground = true, heightDp = 1400)
private fun TransferPreview() {
    TransferMeTheme {
        TransferScreen(
            state = TransferState(
                accountNumber = "123123123123123123",
                amount = "143342342",
                bankSelected = "MCB"
            ),
            onAction = {},
            navController = rememberNavController()
        )
    }
}