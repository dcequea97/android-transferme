package com.cequea.transferme.ui.screens.settings.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cequea.transferme.domain.model.SecurityQuestionModel
import com.cequea.transferme.ui.components.TransferMeButton
import com.cequea.transferme.ui.components.TransferMeMultilineTextField
import com.cequea.transferme.ui.components.TransferMeSimpleHeader
import com.cequea.transferme.ui.navigation.AppScreens
import com.cequea.transferme.ui.screens.settings.SettingsAction
import com.cequea.transferme.ui.screens.settings.SettingsState
import com.cequea.transferme.ui.screens.settings.SettingsViewModel
import com.cequea.transferme.ui.theme.TransferMeTheme
import com.cequea.transferme.utils.Constants
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AnswerQuestionRoot(
    viewModel: SettingsViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.onQuestionSelected.collectLatest { question ->
            navController.navigate(AppScreens.AnswerQuestionScreen)
        }
    }

    AnswerQuestionScreen(
        state = state,
        onAction = viewModel::onAction,
        navController = navController
    )
}

@Composable
private fun AnswerQuestionScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Constants.HORIZONTAL_PADDING.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TransferMeSimpleHeader(
            modifier = Modifier.padding(vertical = Constants.HORIZONTAL_PADDING.dp),
            title = "Security Questions",
            onBackClick = { navController.popBackStack() }
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = state.activeQuestion?.question ?: "",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0XFF5265C0)
        )
        Spacer(Modifier.height(24.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Please, write a short answer in the field below.",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 18.sp
        )
        Spacer(Modifier.height(24.dp))
        TransferMeMultilineTextField(
            placeholder = "Write your answer here...",
            value = state.activeQuestion?.answer ?: "",
            onValueChange = {
                onAction(SettingsAction.AnswerQuestionChanged(it))
            }
        )
        Spacer(Modifier.height(24.dp))
        TransferMeButton(
            onClick = { navController.popBackStack() },
            text = "Save"
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun AnswerQuestionScreenPreview() {
    TransferMeTheme {
        AnswerQuestionScreen(
            state = SettingsState(
                activeQuestion = SecurityQuestionModel("What was your First School’s name?", "aaaa")
            ),
            onAction = {},
            navController = rememberNavController()
        )
    }
}