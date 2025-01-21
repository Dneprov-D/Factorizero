package com.hfad.main.presentation

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import buttons.FzButton
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.hfad.authorization.presentation.InputFieldLogin
import com.hfad.authorization.presentation.InputFieldName
import com.hfad.authorization.presentation.InputFieldPassword
import com.hfad.authorization.presentation.InputFieldSurname
import com.hfad.main.R
//TODO добавить Screen к названию
@Composable
fun CreateNewEmployee(
    viewModel: CreateEmployeeViewModel = hiltViewModel()
) {
    val uiState = viewModel.loginState
    val backgroundColor = MaterialTheme.colorScheme.background

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        InputFieldLogin(
            emailInput = uiState.emailInput,
            onEmailInputChanged = viewModel::onEmailInputChanged
        )

        InputFieldPassword(
            passwordInput = uiState.passwordInput,
            onPasswordInputChanged = viewModel::onPasswordInputChanged
        )

        InputFieldName(
            nameInput = uiState.nameInput,
            onNameInputChanged = viewModel::onNameInputChanged
        )

        InputFieldSurname(
            surnameInput = uiState.surnameInput,
            onSurnameInputChanged = viewModel::onSurnameInputChanged
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
                viewModel.onCreateEmployeeClick()
            },
            text = { Text(text = stringResource(R.string.Register)) }
        )
    }
}


