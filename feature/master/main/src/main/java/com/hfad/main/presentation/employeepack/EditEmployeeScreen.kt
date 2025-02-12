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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import buttons.FzButton
import coil3.compose.rememberAsyncImagePainter
import com.hfad.common.compose.ObserveAsEvents
import com.hfad.navigation.Screen
import com.hfad.ui.R

@Composable
fun EditEmployeeScreen(
    viewModel: EditEmployeeViewModel = hiltViewModel(),
    onEdited: (Screen.EmployeeDetailsScreen) -> Unit
) {
    val state = viewModel.state
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
        id = R.drawable.employeeorc
    )

    ObserveAsEvents(flow = viewModel.navigationEventsChannelFlow) { event ->
        when(event) {
            is EditEmployeeViewModel.NavigationEvent.OnEdited -> onEdited(event.data)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
        text = stringResource(com.hfad.main.R.string.EditProfile),
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Default,
        color = Color.LightGray
    )
        Spacer(modifier = Modifier.height(15.dp))
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(com.hfad.main.R.string.AddPhoto),
            fontSize = 18.sp,
            fontFamily = FontFamily.Default,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(5.dp))
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
        InputFieldName(
            nameInput = state.nameInput,
            onNameInputChanged = viewModel::onNameInputChanged
        )

        InputFieldSurname(
            surnameInput = state.surnameInput,
            onSurnameInputChanged = viewModel::onSurnameInputChanged
        )

        InputFieldJobTitle(
            jobTitleInput = state.jobTitleInput,
            onJobTitleInputChanged = viewModel::onJobTitleInputChanged
        )

        Spacer(modifier = Modifier.height(10.dp))
        if (state.errorState.isNotBlank()) {
            Text(
                text = state.errorState,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }
        FzButton(
            onClick = {
                viewModel.onEditEmployeeClick()
            },
            text = { Text(text = stringResource(com.hfad.main.R.string.ApplyEdit)) }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}