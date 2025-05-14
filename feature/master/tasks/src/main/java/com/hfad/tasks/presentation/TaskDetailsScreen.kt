package com.hfad.tasks.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import buttons.FzButton
import buttons.FzButtonLeadingIconPreview
import buttons.FzOutlinedButton
import com.hfad.main.presentation.employeepack.EmployeeDetailsViewModel
import com.hfad.model.Employee
import com.hfad.model.WorkTask
import com.hfad.ui.R
import com.hfad.ui.profile.uimodel.TaskUiModel

@Composable
fun TaskDetailsScreen(
    viewModel: TaskDetailsViewModel = hiltViewModel(),
    onEditTaskClick: (WorkTask) -> Unit
) {
    TaskDetailsScreenContent(
        state = viewModel.state,
        onEditTaskClick = onEditTaskClick
    )
}


@Composable
fun TaskDetailsScreenContent(
    state: TaskDetailsViewModel.TaskDetailsScreenState,
    onEditTaskClick: (WorkTask) -> Unit
) {

    val textColor = MaterialTheme.colorScheme.onBackground

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    painter = painterResource(id = R.drawable.drawing),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(
                        text = state.task.title,
                        fontSize = 25.sp,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(com.hfad.tasks.R.string.quantitiy),
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = state.task.quantity,
                            fontSize = 16.sp
                        )
                    }
                    FzOutlinedButton(
                        onClick = {},
                        text = { Text(stringResource(com.hfad.tasks.R.string.Reduct)) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
        item {
            Text(
                text = stringResource(com.hfad.tasks.R.string.AtWork),
                modifier = Modifier
                    .fillMaxWidth(),
                color = textColor,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default
            )
            Text(
                text = stringResource(com.hfad.tasks.R.string.NoOne),
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailsPreview() {
    TaskDetailsScreenContent(
        state = TaskDetailsViewModel.TaskDetailsScreenState(
            task = TaskUiModel(
                title = "Task 1",
                quantity = "10",
                key = "1"
            )
        ),
        onEditTaskClick = {}
    )
}