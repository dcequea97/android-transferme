package com.cequea.transferme.ui.screens.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cequea.transferme.domain.model.Category
import com.cequea.transferme.ui.components.BalanceTextWithLabel
import com.cequea.transferme.ui.components.CardItem
import com.cequea.transferme.ui.components.CardMode
import com.cequea.transferme.ui.components.TransferMeSimpleHeader
import com.cequea.transferme.ui.theme.TransferMeTheme
import com.cequea.transferme.utils.Constants
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyWalletRoot(
    navController: NavController,
    viewModel: MyWalletViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MyWalletScreen(
        state = state,
        onAction = viewModel::onAction,
        navController = navController
    )
}

@Composable
private fun MyWalletScreen(
    state: MyWalletState,
    onAction: (MyWalletAction) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = Constants.HORIZONTAL_PADDING.dp)
    ) {
        TransferMeSimpleHeader(
            modifier = Modifier.padding(vertical = Constants.HORIZONTAL_PADDING.dp),
            title = "My Wallet",
            onBackClick = { navController.popBackStack() }
        )

        BalanceTextWithLabel(
            "Current Balance", state.availableBalance,
        )

        MyWalletCategories(
            state.categoriesAvailable,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Text(
            text = "My Cards",
            style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
            color = MaterialTheme.colorScheme.onBackground
        )

        state.cards.forEach { card ->
            CardItem(
                modifier = Modifier.padding(vertical = 12.dp),
                card = card,
                mode = CardMode.DETAILED
            )
        }
    }
}

@Composable
private fun MyWalletCategories(categories: List<Category>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(categories) { category ->
            CategoryItem(
                category,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
    }
}

@Composable
private fun CategoryItem(category: Category, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .height(115.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.background(color = Color(0XFFf6f7fb), shape = RoundedCornerShape(12.dp))
            ) {
                Icon(
                    painter = androidx.compose.ui.res.painterResource(id = category.iconResId),
                    contentDescription = null,
                    tint = Color(0XFF8EDFEB),
                    modifier = Modifier
                        .padding(16.dp)
                        .width(24.dp)
                        .height(24.dp)
                )
            }

            Text(
                text = category.description,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview(showBackground = true, heightDp = 1500)
private fun MyWalletScreenPreview() {
    TransferMeTheme {
        MyWalletScreen(
            state = MyWalletState(),
            onAction = {},
            navController = rememberNavController()
        )
    }
}