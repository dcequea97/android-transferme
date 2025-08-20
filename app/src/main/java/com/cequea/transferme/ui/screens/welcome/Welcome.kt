package com.cequea.transferme.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cequea.transferme.R
import com.cequea.transferme.ui.components.TransferMeButton
import com.cequea.transferme.ui.theme.TransferMeTheme
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WelcomeRoot(
    navController: NavController,
    viewModel: WelcomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    WelcomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun WelcomeScreen(
    state: WelcomeState,
    onAction: (WelcomeAction) -> Unit,
) {
    WelcomeScreenItem(state)
}

@Composable
private fun WelcomeScreenItem(
    state: WelcomeState,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { state.items.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.height(600.dp)
        ) { page ->
            val item = state.items[page]
            Column(
                modifier = modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = item.resourceId),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.8f) // 80% of the width
                        .aspectRatio(1f)    // Keep square ratio (adjust as needed)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = item.title,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = item.body,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )


            }
        }

        TransferMeButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                scope.launch {
                    val nextPage = (pagerState.currentPage + 1) % state.items.size
                    pagerState.animateScrollToPage(nextPage)
                }
            },
            text = "Continue"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(state.items.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (isSelected) 12.dp else 8.dp)
                        .background(
                            color = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.secondary,
                            shape = CircleShape
                        )
                )
            }
        }
    }


    // Indicator
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    TransferMeTheme {
        WelcomeScreen(
            state = WelcomeState(
                items = listOf(
                    WelcomeItem(
                        R.drawable.fast_loading,
                        "Easy, Fast & Trusted",
                        "Fast money transfer and gauranteed safe transactions with others."
                    ),
                    WelcomeItem(
                        R.drawable.fast_loading,
                        "Easy, Fast & Trusted",
                        "Fast money transfer and gauranteed safe transactions with others."
                    ),
                    WelcomeItem(
                        R.drawable.fast_loading,
                        "Easy, Fast & Trusted",
                        "Fast money transfer and gauranteed safe transactions with others."
                    )
                )
            ),
            onAction = {}
        )
    }
}
