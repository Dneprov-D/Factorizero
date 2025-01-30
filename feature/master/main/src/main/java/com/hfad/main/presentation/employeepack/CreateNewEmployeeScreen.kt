package com.hfad.main.presentation.employeepack

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import buttons.FzButton
import coil3.compose.rememberAsyncImagePainter
import com.hfad.authorization.presentation.InputFieldJobTitle
import com.hfad.authorization.presentation.InputFieldLogin
import com.hfad.authorization.presentation.InputFieldName
import com.hfad.authorization.presentation.InputFieldPassword
import com.hfad.authorization.presentation.InputFieldSurname
import com.hfad.main.R

@Composable
fun CreateNewEmployeeScreen(
    viewModel: CreateEmployeeViewModel = hiltViewModel()
) {
    val uiState = viewModel.loginState
    val backgroundColor = MaterialTheme.colorScheme.background
    val selectedImageUri = rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri.value = uri
    }
    val placeholderImage = painterResource(
        id = com.hfad.ui.R.drawable.employeeorc
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(170.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    imageLauncher.launch("image/*")
                },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            painter = if (selectedImageUri.value != null) {
                rememberAsyncImagePainter(model = selectedImageUri.value)
            } else {
                placeholderImage
            }
        )
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

        InputFieldJobTitle(
            jobTitleInput = uiState.jobTitleInput,
            onJobTitleInputChanged = viewModel::onJobTitleInputChanged
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
        Spacer(modifier = Modifier.height(10.dp))
    }
}