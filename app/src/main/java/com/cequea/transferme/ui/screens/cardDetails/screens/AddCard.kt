package com.cequea.transferme.ui.screens.cardDetails.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cequea.transferme.R
import com.cequea.transferme.domain.model.Card
import com.cequea.transferme.domain.model.CardType
import com.cequea.transferme.domain.model.Currency
import com.cequea.transferme.ui.components.CardItem
import com.cequea.transferme.ui.components.CardMode
import com.cequea.transferme.ui.components.TransferMeButton
import com.cequea.transferme.ui.components.TransferMeSimpleHeader
import com.cequea.transferme.ui.components.UnderlinedTextField
import com.cequea.transferme.ui.screens.cardDetails.CardDetailsAction
import com.cequea.transferme.ui.screens.cardDetails.CardDetailsUIState
import com.cequea.transferme.ui.screens.cardDetails.CardDetailsViewModel
import com.cequea.transferme.ui.theme.TransferMeTheme
import com.cequea.transferme.utils.Constants
import com.cequea.transferme.utils.visualTransfomations.AccountVisualTransformation
import com.cequea.transferme.utils.visualTransfomations.ExpirationDateVisualTransformation

enum class AddCardStep {
    FORM,
    SELECT_COLOR
}

@Composable
fun AddCardRoot(
    navController: NavController,
    viewModel: CardDetailsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddCardScreen(
        state = state,
        onAction = viewModel::onAction,
        navController = navController
    )
}

@Composable
private fun AddCardScreen(
    state: CardDetailsUIState,
    onAction: (CardDetailsAction) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Constants.HORIZONTAL_PADDING.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var title: String
        var descriptionText: String

        when (state.addCardStep) {
            AddCardStep.FORM -> {
                title = "Add Card"
                descriptionText =
                    "To add a new card, please fill out the fields below carefully in order to add card successfully."
            }

            AddCardStep.SELECT_COLOR -> {
                title = "Card Color"
                descriptionText =
                    "Please, select a color to differentiat this card from other cards you have attached and organize your cards better."
            }
        }
        TransferMeSimpleHeader(
            modifier = Modifier.padding(vertical = Constants.HORIZONTAL_PADDING.dp),
            title = title,
            onBackClick = { navController.popBackStack() }
        )
        Spacer(Modifier.height(24.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = descriptionText,
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 18.sp
        )
        Spacer(Modifier.height(24.dp))
        when (state.addCardStep) {
            AddCardStep.FORM -> AddCardForm(state, onAction)
            AddCardStep.SELECT_COLOR -> AddCardSelectColor(state, navController)
        }
    }
}

@Composable
fun AddCardSelectColor(
    state: CardDetailsUIState,
    navController: NavController
) {
    Column {
        val colors = listOf(
            Color(0xFF8CD9E9),
            Color(0xFF34A853),
            Color(0xFF0166FF),
            Color(0xFFF59D31),
            Color(0xFFFC6020),
            Color(0xFF009CDE),
            Color(0xFFE80B26),
            Color(0xFFFBBC05),
            Color(0xFF979797),
            Color(0xFF1E1E1E),
            Color(0xFF000000),
            Color(0xFF003087),
            Color(0xFF001A4D),
            Color(0xFF392993),
            Color(0xFF6875E2),
        )
        val colorSelected = remember { mutableStateOf(colors.first()) }
        val animatedColor by animateColorAsState(
            targetValue = colorSelected.value,
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        )

        CardItem(
            card = state.newCardData,
            modifier = Modifier.padding(end = 8.dp),
            mode = CardMode.DETAILED,
            backgroundColor = animatedColor
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = Constants.HORIZONTAL_PADDING.dp)
                .heightIn(max = 300.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(colors) { color ->
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                            .size(50.dp)
                            .shadow(
                                elevation = 3.dp,
                                shape = CircleShape,
                                clip = false
                            )
                            .background(color, CircleShape)
                            .clickable(onClick = { colorSelected.value = color })
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                            .size(50.dp)
                            .shadow(
                                elevation = 3.dp,
                                shape = CircleShape,
                                clip = false
                            )
                            .background(Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.add),
                            contentDescription = "Add Color",
                            tint = Color(0XFF0166FF),
                        )
                    }
                }
            }
        }
        TransferMeButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp),
            text = "Confirm",
            onClick = { navController.popBackStack() },
        )
    }
}

@Composable
private fun AddCardForm(
    state: CardDetailsUIState,
    onAction: (CardDetailsAction) -> Unit
) {
    Column {
        UnderlinedTextField(
            label = "Card Number",
            value = state.newCardData.cardNumber,
            onValueChange = {
                onAction(CardDetailsAction.OnNewCardNumberChange(it))
            },
            visualTransformation = AccountVisualTransformation('-'),
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        )
        Spacer(Modifier.height(22.dp))

        UnderlinedTextField(
            label = "Holder’s Name",
            value = state.newCardData.cardHolderName,
            onValueChange = {
                onAction(CardDetailsAction.OnNewCardHolderNameChange(it))
            },
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Unspecified
        )
        Spacer(Modifier.height(22.dp))

        UnderlinedTextField(
            label = "Expiry Date",
            value = state.newCardData.expirationDate,
            onValueChange = {
                onAction(CardDetailsAction.OnNewCardExpiryDateChange(it))
            },
            visualTransformation = ExpirationDateVisualTransformation(),
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        )
        Spacer(Modifier.height(22.dp))
        val cvv = remember { mutableStateOf("") }
        UnderlinedTextField(
            label = "CVV",
            value = cvv.value,
            onValueChange = {
                cvv.value = it.filter { char -> char.isDigit() }.take(3)
            },
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        )
        Spacer(Modifier.height(22.dp))
        TransferMeButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp),
            text = "Confirm",
            onClick = { onAction(CardDetailsAction.OnConfirmAddCardFormClicked) },
        )
    }
}


@Composable
@Preview(showBackground = true)
private fun AddCardScreenPreview() {
    TransferMeTheme {
        AddCardScreen(
            state = CardDetailsUIState(
                addCardStep = AddCardStep.FORM,
                newCardData = Card(
                    cardNumber = "1234",
                    cardHolderName = "Juan Perez",
                    expirationDate = "1224",
                    cardType = CardType.VISA,
                    balance = 0.0,
                    currency = Currency.USD
                )
            ),
            onAction = {},
            navController = rememberNavController()
        )
    }
}