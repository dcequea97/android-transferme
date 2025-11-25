package com.cequea.transferme.ui.screens.login

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cequea.transferme.R
import com.cequea.transferme.ui.components.PinPad
import com.cequea.transferme.ui.components.TransferMeButton
import com.cequea.transferme.ui.components.TransferMeMultilineTextField
import com.cequea.transferme.ui.components.TransferMeSimpleHeader
import com.cequea.transferme.ui.components.UnderlinedTextField
import com.cequea.transferme.ui.navigation.AppScreens
import com.cequea.transferme.ui.theme.TransferMeColors
import com.cequea.transferme.ui.theme.TransferMeTheme

@Composable
fun LoginRoot(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginScreen(
        state = state,
        onAction = viewModel::onAction,
        onLoginClicked = {
            navController.navigate(AppScreens.HomeScreen)
        }
    )
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    onLoginClicked: () -> Unit
) {
    Crossfade(targetState = state.activeScreen, animationSpec = tween(250)) { screen ->
        Box(modifier = Modifier.padding(horizontal = 32.dp)) {
            when (screen) {
                LoginScreens.Login ->       Login(onAction, onLoginClicked)
                LoginScreens.SignUp ->      SignUp(onAction)
                LoginScreens.Profile ->     Profile(onAction)
                LoginScreens.PhoneNumber -> PhoneNumber(onAction)
                LoginScreens.SecurityQuestions ->
                    SecurityQuestions(onAction)
                LoginScreens.PinCode ->
                    PinCode(onAction, state, onLoginClicked)
            }
        }
    }
}

@Composable
private fun PinCode(onAction: (LoginAction) -> Unit, state: LoginState, onLoginClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TransferMeSimpleHeader(
            title = "Login",
            { onAction(LoginAction.OnLoginScreenChanged(LoginScreens.Login)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val descriptionText = when (state.pinCodeType) {
            LoginPinCodeType.Set -> "Please set your\nPin Code"
            LoginPinCodeType.Existing -> "Please enter your Pin Code"
        }
        Text(
            text = descriptionText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Enter Pin Code (5 digits)",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        var pinCodeSet by remember { mutableStateOf(emptySet<String>()) }
        PinPad(
            onKeyPress = {
                if (!it.isDigitsOnly()) return@PinPad

                pinCodeSet = if (pinCodeSet.contains(it)) {
                    pinCodeSet - it
                } else {
                    if (pinCodeSet.size < 5) pinCodeSet + it else pinCodeSet
                }
            },
            highlightedKeys = pinCodeSet
        )

        Spacer(modifier = Modifier.height(32.dp))
        TransferMeButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                onAction(LoginAction.SetPinCodeTextHeader(LoginPinCodeType.Set))
                onLoginClicked()
            },
            text = "Go"
        )
    }
}

@Composable
private fun SecurityQuestions(onAction: (LoginAction) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TransferMeSimpleHeader(
            title = "Security\nQuestions",
            { onAction(LoginAction.OnLoginScreenChanged(LoginScreens.PhoneNumber)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Please, write a short answer in the field below.",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        var multitextValue by remember { mutableStateOf("") }
        TransferMeMultilineTextField(
            placeholder = "Write your answer here...",
            value = multitextValue,
            onValueChange = {
                multitextValue = it
            }
        )

        Spacer(modifier = Modifier.height(32.dp))
        TransferMeButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                onAction(LoginAction.SetPinCodeTextHeader(LoginPinCodeType.Set))
                onAction(LoginAction.OnLoginScreenChanged(LoginScreens.PinCode))
            },
            text = "Save"
        )
    }
}

@Composable
private fun PhoneNumber(onAction: (LoginAction) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TransferMeSimpleHeader(
            title = "Phone Number",
            { onAction(LoginAction.OnLoginScreenChanged(LoginScreens.Profile)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            style = MaterialTheme.typography.headlineLarge,
            text = "What was your\nFirst School’s\nName?",
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Please, write a short answer in the field below.",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        var phoneNumberValue by remember { mutableStateOf("") }
        UnderlinedTextField(
            label = "*Phone Number",
            value = phoneNumberValue,
            onValueChange = {
                phoneNumberValue = it
            },
            visualTransformation = PasswordVisualTransformation(),
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(32.dp))
        TransferMeButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                onAction(LoginAction.OnLoginScreenChanged(LoginScreens.SecurityQuestions))
            },
            text = "Confirm"
        )
    }
}

@Composable
private fun Profile(onAction: (LoginAction) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TransferMeSimpleHeader(
            title = "Profile",
            { onAction(LoginAction.OnLoginScreenChanged(LoginScreens.SignUp)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Please set up your profile"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(150.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.width(24.dp),
                painter = painterResource(id = R.drawable.upload),
                contentDescription = "Upload photo",
            )
        }

        var firstNameValue by remember { mutableStateOf("") }
        UnderlinedTextField(
            label = "First Name",
            value = firstNameValue,
            onValueChange = {
                firstNameValue = it
            },
            imeAction = ImeAction.Next,
        )
        Spacer(modifier = Modifier.height(32.dp))
        var lastNameValue by remember { mutableStateOf("") }
        UnderlinedTextField(
            label = "Last Name",
            value = lastNameValue,
            onValueChange = {
                lastNameValue = it
            },
            imeAction = ImeAction.Done,
        )
        Spacer(modifier = Modifier.height(32.dp))
        TransferMeButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                onAction(LoginAction.OnLoginScreenChanged(LoginScreens.PhoneNumber))
            },
            text = "Set"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUp(onAction: (LoginAction) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.fillMaxWidth(0.4f),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.headlineLarge
        )

        var emailValue by remember { mutableStateOf("") }
        UnderlinedTextField(
            label = "Email Address",
            value = emailValue,
            onValueChange = {
                emailValue = it
            },
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        )

        var passwordValue by remember { mutableStateOf("") }
        UnderlinedTextField(
            label = "Password",
            value = passwordValue,
            onValueChange = {
                passwordValue = it
            },
            visualTransformation = PasswordVisualTransformation(),
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Password
        )

        var confirmPasswordValue by remember { mutableStateOf("") }
        UnderlinedTextField(
            label = "Confirm Password",
            value = confirmPasswordValue,
            onValueChange = {
                confirmPasswordValue = it
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password
        )

        Spacer(modifier = Modifier.height(16.dp))

        TransferMeButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { onAction(LoginAction.OnLoginScreenChanged(LoginScreens.Profile)) },
            text = "Sign Up"
        )

        SocialNetworkMenu(
            text = "Already have an account? ",
            highlightText = "Login",
            onTextClicked = {
                onAction(LoginAction.OnLoginScreenChanged(LoginScreens.Login))
            }
        )
    }
}

@Composable
private fun Login(
    onAction: (LoginAction) -> Unit,
    onLoginClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.fillMaxWidth(0.4f),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge
        )

        var phoneValue by remember { mutableStateOf("") }
        UnderlinedTextField(
            label = "Phone Number",
            value = phoneValue,
            onValueChange = {
                phoneValue = it
            },
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(16.dp))

        TransferMeButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = onLoginClicked,
            text = "Login"
        )

        SocialNetworkMenu(
            text = "Don’t have an account? ",
            highlightText = "Sign Up",
            onTextClicked = {
                onAction(LoginAction.OnLoginScreenChanged(LoginScreens.SignUp))
            }
        )
    }
}

@Composable
private fun SocialNetworkMenu(
    text: String,
    highlightText: String,
    onTextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .weight(0.4f)
                    .background(TransferMeColors.SoftGray)
            )

            Text(
                modifier = Modifier.weight(0.2f),
                text = "or",
                textAlign = TextAlign.Center,
                color = TransferMeColors.SoftGray
            )

            Box(
                modifier = Modifier
                    .height(1.dp)
                    .weight(0.4f)
                    .background(TransferMeColors.SoftGray)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "App Logo",
                modifier = Modifier.height(60.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(id = R.drawable.facebook_logo),
                contentDescription = "App Logo",
                modifier = Modifier.height(60.dp),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onTextClicked
            ),
            text = buildAnnotatedString {
                append(text)
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(highlightText)
                }
            },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    TransferMeTheme {
        LoginScreen(
            state = LoginState(
                activeScreen = LoginScreens.PinCode
            ),
            onAction = {},
            onLoginClicked = {}
        )
    }
}
