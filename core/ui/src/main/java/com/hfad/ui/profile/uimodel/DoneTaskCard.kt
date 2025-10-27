package com.hfad.ui.profile.uimodel

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.ui.R
import com.hfad.ui.profile.TaskCard

@Composable
fun DoneTaskCard(
    task: TaskUiModel,
    onCardClicked: (TaskUiModel) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable { onCardClicked(task) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB1FFB1)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(id = R.drawable.drawing),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(25.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontSize = 20.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Количество: ",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = task.quantity,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoneTaskCardW() {
    DoneTaskCard(
        task = TaskUiModel(
            title = "Задача 1",
            quantity = "10",
            key = "1",
            doneCount = 2,
            done = false
        ),
        onCardClicked = {}
    )
}