package com.hfad.tasks.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hfad.model.WorkTask
import com.hfad.ui.profile.TaskCard

@Composable
fun DoneTasksScreen(
    viewModel: DoneTasksScreenViewModel = hiltViewModel()
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
                text = "Выполненные задачи",
                modifier = Modifier
                    .padding(7.dp)
                    .fillMaxWidth(),
                color = textColor,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Нет выполненных задач",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(state.tasksList) { task ->
                    TaskCard(
                        task = task,
                        onCardClicked = {}
                    )
                }
            }
        }
    }
}
