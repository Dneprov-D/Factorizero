package com.hfad.main.presentation

import Icons.FzIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hfad.designsystem.components.theme.LightColorScheme
import com.hfad.main.R
import com.hfad.model.Employee
import com.hfad.model.WorkTask
import com.hfad.ui.profile.TaskCard

@Composable
fun MainEmployeeScreen(
    viewModel: MainEmployeeScreenViewModel = hiltViewModel(),
    onTaskClick: (WorkTask) -> Unit
) {
    val state = viewModel.state
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Text(
                text = stringResource(R.string.AssignedTasksText),
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
                items(state.tasksList) { task ->
                    TaskCard(
                        task = task,
                        onCardClicked = {
                            onTaskClick(
                                WorkTask(
                                    key = it.key,
                                    title = it.title,
                                    quantity = it.quantity
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}