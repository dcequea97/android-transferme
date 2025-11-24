package com.cequea.transferme.ui.screens.cardDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.transferme.domain.model.Card
import com.cequea.transferme.domain.model.CardType
import com.cequea.transferme.domain.model.Currency
import com.cequea.transferme.ui.navigation.AppScreens
import com.cequea.transferme.ui.screens.cardDetails.screens.AddCardStep
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CardDetailsViewModel : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CardDetailsUIState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CardDetailsUIState()
        )

    private val _onNavigate = Channel<AppScreens>()
    val onNavigate = _onNavigate.receiveAsFlow()

    fun onAction(action: CardDetailsAction) {
        when (action) {
            CardDetailsAction.AddCardClicked -> {
                _onNavigate.trySend(AppScreens.AddCardScreen)
                _state.update {
                    it.copy(
                        addCardStep = AddCardStep.FORM,
                        newCardData = Card(
                            cardNumber = "",
                            cardHolderName = "",
                            expirationDate = "",
                            cardType = CardType.VISA,
                            balance = 0.0,
                            currency = Currency.USD
                        )
                    )
                }
            }

            is CardDetailsAction.CurrencySelected -> {
                _state.update {
                    it.copy(
                        selectedCurrency = action.currency
                    )
                }
            }

            is CardDetailsAction.OnNewCardExpiryDateChange -> {
                _state.update {
                    it.copy(newCardData = it.newCardData.copy(expirationDate = action.newData.filter { char -> char.isDigit() }
                        .take(4)))
                }
            }

            is CardDetailsAction.OnNewCardHolderNameChange -> {
                _state.update {
                    it.copy(
                        newCardData = it.newCardData.copy(
                            cardHolderName = action.newData.take(
                                50
                            )
                        )
                    )
                }
            }

            is CardDetailsAction.OnNewCardNumberChange -> {
                _state.update {
                    it.copy(newCardData = it.newCardData.copy(cardNumber = action.newData.filter { char -> char.isDigit() }
                        .take(20)))
                }
            }

            CardDetailsAction.OnConfirmAddCardFormClicked -> {
                _state.update { it.copy(addCardStep = AddCardStep.SELECT_COLOR) }
            }
        }
    }
}