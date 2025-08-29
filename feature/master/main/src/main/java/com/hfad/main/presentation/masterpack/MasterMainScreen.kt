package com.hfad.main.presentation.masterpack

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hfad.main.R
import com.hfad.model.Employee
import com.hfad.ui.profile.EmployeeCard
import androidx.compose.runtime.LaunchedEffect


@Composable

fun MasterMainScreen(
    viewModel: MasterMainViewModel = hiltViewModel(),
    onEmployeeClick: (Employee) -> Unit,
) {
    val state = viewModel.state
    val textColor = MaterialTheme.colorScheme.onBackground

    LaunchedEffect(Unit) {
        viewModel.refreshEmployees()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.EmployeeText),
            modifier = Modifier
                .padding(7.dp)
                .fillMaxWidth(),
            color = textColor,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(state.employeeList) { employee ->
                EmployeeCard(
                    employee = employee,
                    onCardClicked = {
                        onEmployeeClick(
                            Employee(
                                key = it.key,
                                name = it.name,
                                surname = it.surname,
                                jobTitle = it.jobTitle
                            )
                        )
                    }
                )
            }
        }
    }
}