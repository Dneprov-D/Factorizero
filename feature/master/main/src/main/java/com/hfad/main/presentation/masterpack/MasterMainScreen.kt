package com.hfad.main.presentation.masterpack

import Icons.FzIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import buttons.FzOutlinedButton
import com.hfad.designsystem.components.theme.LightColorScheme
import com.hfad.main.R
import com.hfad.model.Employee
import com.hfad.ui.profile.EmployeeCard

@Composable
fun MasterMainScreen(
    viewModel: MasterMainViewModel = hiltViewModel(),
    onEmployeeClick: (Employee) -> Unit
) {
    val state = viewModel.state
    val backgroundColor = MaterialTheme.colorScheme.background

    Scaffold(
        modifier = Modifier
            .background(color = backgroundColor)
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier
                .padding(innerPadding)
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