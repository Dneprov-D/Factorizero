package com.hfad.main.presentation.employeepack


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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import buttons.FzOutlinedButton
import com.hfad.model.Employee
import com.hfad.ui.R

@Composable
fun EmployeeDetailsScreen(
    viewModel: EmployeeDetailsViewModel = hiltViewModel(),
    onEditClick: (Employee) -> Unit
) {

    val state = viewModel.state
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
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    painter = painterResource(id = R.drawable.employeeorc),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(
                        text = "${state.employee.name} ${state.employee.surname}",
                        fontSize = 25.sp,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = state.employee.jobTitle,
                        fontSize = 20.sp
                    )
                    FzOutlinedButton(
                        onClick = {
                            onEditClick(
                                Employee(
                                    key = state.employee.key,
                                    name = state.employee.name,
                                    surname = state.employee.surname,
                                    jobTitle = state.employee.jobTitle
                                )
                            )
                        },
                        text = { Text(stringResource(com.hfad.main.R.string.Reduct)) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null
                            )
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Text(
                    text = stringResource(com.hfad.main.R.string.TasksInWorkText),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    color = textColor
                )

                FzOutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { /*TODO*/ },
                    text = { Text(stringResource(com.hfad.main.R.string.EditTasksText)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.EditNote,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}