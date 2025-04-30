package com.hfad.tasks.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import buttons.FzButton
import coil3.compose.rememberAsyncImagePainter
import com.hfad.common.compose.ObserveAsEvents
import com.hfad.main.R
import com.hfad.main.presentation.employeepack.CreateEmployeeViewModel.NavigationEvent
import com.hfad.model.Employee
import com.hfad.ui.profile.EmployeeCard
import com.hfad.ui.profile.SelectedEmployeeCard
import com.hfad.ui.profile.uimodel.EmployeeUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun CreateNewTaskScreen(
    viewModel: CreateNewTaskViewModel = hiltViewModel(),
    onTaskCreated: () -> Unit
) {
    val state = viewModel.state
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground
    val placeholderImage = painterResource(id = com.hfad.ui.R.drawable.drawing)
    val selectedImageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
//    var selectedEmployee by rememberSaveable { mutableStateOf<EmployeeUiModel?>(null) }
    val isFormValid = state.titleInput.isNotEmpty() &&
            state.quantityInput.isNotEmpty()

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> selectedImageUri.value = uri }

    ObserveAsEvents(flow = viewModel.navigationEventsChannelFlow) { event ->
        when(event) {
            is CreateNewTaskViewModel.NavigationEvent.OnTaskCreated -> onTaskCreated()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.NewTaskText),
                modifier = Modifier.weight(1f),
                color = textColor,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
            )
            FzButton(
                onClick = {
                    if (isFormValid) {
                        viewModel.onTaskCreateClick()
                    }
                },
                enabled = isFormValid,
                modifier = Modifier.padding(end = 8.dp),
                text = { Text(text = stringResource(R.string.Done)) }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                Text(
                    text = stringResource(R.string.AddDraw),
                    modifier = Modifier
                        .padding(7.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.LightGray
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .size(170.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { imageLauncher.launch("image/*") },
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        painter = if (selectedImageUri.value != null) {
                            rememberAsyncImagePainter(model = selectedImageUri.value)
                        } else {
                            placeholderImage
                        }
                    )
                }

                TitleTaskInputField(
                    value = state.titleInput,
                    onTitleInputChange = viewModel::onTitleInputChanged
                    )

                TaskQuantityInputField(
                    value = state.quantityInput,
                    onQuantityInputChange = viewModel::onQuantityInputChanged
                )

                Text(
                    text = stringResource(R.string.SelectAnEmployee),
                    modifier = Modifier
                        .padding(7.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.LightGray
                )
            }

            items(state.employeeList) { employee ->
                SelectedEmployeeCard(
                    employee = employee
                )
            }
        }
    }
}