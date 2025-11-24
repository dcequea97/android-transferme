package com.cequea.transferme.ui.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.transferme.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class WelcomeViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(WelcomeState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                setItems()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = WelcomeState()
        )

    fun onAction(action: WelcomeAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

    fun setItems(){
        val items = listOf(
            WelcomeItem(
                R.drawable.fast_loading,
                "Easy, Fast & Trusted",
                "Fast money transfer and gauranteed safe transactions with others."
            ),
            WelcomeItem(
                R.drawable.savings_pana,
                "Saving Your Money",
                "Track the progress of your savings and start a habit of saving with TransferMe."
            ),
            WelcomeItem(
                R.drawable.mobile_payments_rafiki,
                "Free Transactions",
                "Provides the quality of the financial system with free money transactions without any fees."
            ),
            WelcomeItem(
                R.drawable.currencyrafiki,
                "International Transactions",
                "Provides the 100% freedom of the financial management with lowest fees on International transactions."
            ),
            WelcomeItem(
                R.drawable.wallet_amico_,
                "Multiple Credit Cards",
                "Provides the 100% freedom of the financial management with Multiple Payment Options for local & International Payments."
            ),
            WelcomeItem(
                R.drawable.plain_credit_card_pana,
                "Bills Payment Made Easy",
                "Pay monthly or daily bills at home in a site of TransferMe."
            ),
            WelcomeItem(
                R.drawable.group_64,
                "Color Your Cards",
                "Provides better cards management when using Multiple Cards by using a different color for each payment method."
            ),
        )

        _state.update { it.copy(items = items) }
    }
}
