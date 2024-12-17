package com.hfad.authorization.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import buttons.FzButton
import com.hfad.authorization.R
import com.hfad.common.compose.ObserveAsEvents
import com.hfad.navigation.Screen

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToMainScreen: (Screen.MainScreenDataObject) -> Unit
) {

    val uiState = viewModel.loginState
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground

    ObserveAsEvents(flow = viewModel.navigationEventsChannelFlow) { event ->
        when(event) {
           is NavigationEvent.OnSignedIn -> {
                onNavigateToMainScreen(event.data)
            }

            is NavigationEvent.OnRegistered -> {
                onNavigateToMainScreen(event.data)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.factorizerText),
            fontSize = 32.sp,
            fontFamily = FontFamily.Serif,
            color = textColor
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.EnterToProfileText),
            fontSize = 17.sp,
            color = textColor
        )

        InputFieldLogin(
            emailInput = uiState.emailInput,
            onEmailInputChanged = viewModel::onEmailInputChanged
        )

        InputFieldPassword(
            passwordInput = uiState.passwordInput,
            onPasswordInputChanged = viewModel::onPasswordInputChanged
        )

        Spacer(modifier = Modifier.height(10.dp))
        if (uiState.errorState.isNotBlank()) {
            Text(
                text = uiState.errorState,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }
        FzButton(
            onClick = {
                viewModel.onSignInClick()

            },
            text = { Text(text = stringResource(R.string.LogIn)) }
        )
        Spacer(modifier = Modifier.width(10.dp))
        FzButton(
            onClick = {
                viewModel.onCreateAccountClick()
            },
            text = { Text(text = stringResource(R.string.CreateAnAccount)) }
        )
    }
}