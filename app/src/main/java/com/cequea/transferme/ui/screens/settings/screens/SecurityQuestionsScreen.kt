package com.cequea.transferme.ui.screens.settings.screens

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cequea.transferme.R
import com.cequea.transferme.domain.model.SecurityQuestionModel
import com.cequea.transferme.ui.components.TransferMeButton
import com.cequea.transferme.ui.components.TransferMeSimpleHeader
import com.cequea.transferme.ui.navigation.AppScreens
import com.cequea.transferme.ui.screens.settings.SettingsAction
import com.cequea.transferme.ui.screens.settings.SettingsState
import com.cequea.transferme.ui.screens.settings.SettingsViewModel
import com.cequea.transferme.ui.theme.TransferMeTheme
import com.cequea.transferme.utils.Constants
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SecurityQuestionsRoot(
    viewModel: SettingsViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.onQuestionSelected.collectLatest { question ->
            navController.navigate(AppScreens.AnswerQuestionScreen)
        }
    }

    SecurityQuestionsScreen(
        state = state,
        onAction = viewModel::onAction,
        navController = navController
    )
}

@Composable
private fun SecurityQuestionsScreen(
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
            text = "Please, add atleast 3 Questions to keep your account secured in case you forget your credentials.",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 18.sp
        )
        Spacer(Modifier.height(24.dp))
        state.questions.forEach { question ->
            QuestionItem(
                question = question,
                onClick = {
                    onAction(
                        SettingsAction.OnSecurityQuestionSelected(question)
                    )
                    navController.popBackStack()
                },
                modifier = Modifier
            )
        }
        Spacer(Modifier.height(24.dp))
        TransferMeButton(
            onClick = { navController.popBackStack() },
            text = "Save"
        )
    }
}

@Composable
private fun QuestionItem(
    question: SecurityQuestionModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isQuestionAnswered = question.answer != null && question.answer.isNotEmpty()
    val backgroundColor = if (isQuestionAnswered) Color(0XFF0166FF) else Color(0XFF001A4D)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = backgroundColor.copy(alpha = 0.05f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 12.dp)
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(if(isQuestionAnswered) 0.85f else 1f),
                text = question.question,
                color = Color(0XFF5164BF)
            )

            if (isQuestionAnswered) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            color = Color(0XFF5265C0),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.padding(6.dp),
                        painter = painterResource(R.drawable.check),
                        contentDescription = "Success",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SecurityQuestionsScreenPreview() {
    TransferMeTheme {
        SecurityQuestionsScreen(
            state = SettingsState(
                questions = listOf(
                    SecurityQuestionModel("What was your First School’s name?", "aaaaa"),
                    SecurityQuestionModel("What is your favorite food?", null),
                    SecurityQuestionModel("What city were you born in?", null),
                    SecurityQuestionModel("What is your pet’s name?", null),
                    SecurityQuestionModel("What is your mother’s maiden name?", null),
                )
            ),
            onAction = {},
            navController = rememberNavController()
        )
    }
}