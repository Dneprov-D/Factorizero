package com.hfad.tasks.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import buttons.FzButton
import buttons.FzOutlinedButton
import com.hfad.common.compose.ObserveAsEvents
import com.hfad.model.WorkTask
import com.hfad.ui.R

@Composable
fun TaskDetailsScreen(
    viewModel: TaskDetailsViewModel = hiltViewModel(),
    onEditTaskClick: (WorkTask) -> Unit,
    onClose: () -> Unit
) {
    val state = viewModel.state
    val textColor = MaterialTheme.colorScheme.onBackground
    var isFullScreenImageVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    ObserveAsEvents(flow = viewModel.navigationEventsChannelFlow) { event ->
        when (event) {
            TaskDetailsViewModel.NavigationEvent.OnClosed -> onClose()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { isFullScreenImageVisible = true },
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
                        Spacer(modifier = Modifier.width(5.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(com.hfad.tasks.R.string.Done),
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = state.task.doneCount.toString(),
                                fontSize = 16.sp
                            )
                        }

                        if (state.task.doneCount.toString() == state.task.quantity && !state.task.done) {
                            Spacer(modifier = Modifier.height(8.dp))
                            FzButton(
                                onClick = {
                                    viewModel.closeTask()
                                    Toast.makeText(
                                        context,
                                        "Задача ${state.task.title} закрыта!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                text = {
                                    Text(stringResource(com.hfad.tasks.R.string.Close))
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        FzOutlinedButton(
                            onClick = {
                                onEditTaskClick(
                                    WorkTask(
                                        key = state.task.key,
                                        title = state.task.title,
                                        quantity = state.task.quantity,
                                        doneCount = state.task.doneCount,
                                        done = state.task.done
                                    )
                                )
                            },
                            text = { Text(stringResource(com.hfad.tasks.R.string.Reduct)) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
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

        if (isFullScreenImageVisible) {
            FullScreenImage(
                imageResId = R.drawable.drawing,
                onDismiss = { isFullScreenImageVisible = false }
            )
        }
    }
}

@Composable
fun FullScreenImage(
    imageResId: Int,
    onDismiss: () -> Unit
) {
    var scale by rememberSaveable { mutableStateOf(1f) }
    var offsetX by rememberSaveable { mutableStateOf(0f) }
    var offsetY by rememberSaveable { mutableStateOf(0f) }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Black.copy(alpha = 0.9f)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offsetX,
                        translationY = offsetY
                    )
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = (scale * zoom).coerceIn(1f, 5f)

                            val maxX = (scale - 1) * size.width / 2
                            val maxY = (scale - 1) * size.height / 2

                            offsetX = (offsetX + pan.x * scale).coerceIn(-maxX, maxX)
                            offsetY = (offsetY + pan.y * scale).coerceIn(-maxY, maxY)
                        }
                    }
            )

            IconButton(
                onClick = {
                    onDismiss()
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun TaskDetailsPreview() {
//    TaskDetailsScreenContent(
//        state = TaskDetailsViewModel.TaskDetailsScreenState(
//            task = TaskUiModel(
//                title = "Task 1",
//                quantity = "10",
//                key = "1"
//            )
//        ),
//        onEditTaskClick = {}
//    )
//}
