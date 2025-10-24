package com.hfad.main.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.imePadding
import androidx.hilt.navigation.compose.hiltViewModel
import buttons.FzButton
import com.hfad.ui.R
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.RadioButton
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer

@Composable
fun EmployeeTaskDetailsScreen(
    viewModel: EmployeeTaskDetailsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val totalQuantityText = state.task.quantity
    val totalQuantity = remember(totalQuantityText) { totalQuantityText.toIntOrNull() }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val isInvalidInput = remember(state.editorText, totalQuantity) {
        val v = state.editorText.toIntOrNull()
        totalQuantity != null && v != null && v > totalQuantity
    }

    val editorTextStyle = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = if (isInvalidInput) Color.Red else MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.End
    )

    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val measuredTextWidthPx = remember(state.editorText, editorTextStyle) {
        textMeasurer.measure(
            text = state.editorText.ifEmpty { "0" },
            style = editorTextStyle
        ).size.width
    }
    val inputWidth = with(density) { measuredTextWidthPx.toDp() } + 8.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { viewModel.setFullScreenImageVisible(true) },
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
                                text = stringResource(com.hfad.main.R.string.quantitiy),
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = state.task.quantity,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Шаг изменения:",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val multipliers = listOf(1, 2, 5, 10)
                        multipliers.forEach { multiplier ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable { viewModel.setMultiplier(multiplier) }
                            ) {
                                Text(
                                    text = "x$multiplier",
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                RadioButton(
                                    selected = state.selectedMultiplier == multiplier,
                                    onClick = { viewModel.setMultiplier(multiplier) }
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.decrementCount() },
                        modifier = Modifier.size(80.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Remove,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        BasicTextField(
                            value = state.editorText,
                            onValueChange = { new ->
                                viewModel.updateEditorText(new)
                            },
                            singleLine = true,
                            textStyle = editorTextStyle,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    viewModel.validateAndSaveEditorText(totalQuantity)
                                    focusManager.clearFocus()
                                }
                            ),
                            modifier = Modifier
                                .alignByBaseline()
                                .widthIn(min = 24.dp, max = inputWidth)
                                .clickable { focusRequester.requestFocus() }
                                .focusRequester(focusRequester)
                        )
                        Text(
                            text = " / ",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.alignByBaseline()
                        )
                        Text(
                            text = totalQuantityText,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            modifier = Modifier.alignByBaseline()
                        )
                    }

                    IconButton(
                        onClick = { viewModel.incrementCount(totalQuantity) },
                        modifier = Modifier.size(80.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FzButton(
                        onClick = {
                            if (state.isTaskCompleted) {
                                // TODO: Добавить логику отправки сообщения мастеру
                            }
                        },
                        enabled = state.isTaskCompleted,
                        text = {
                            Text(
                                stringResource(com.hfad.main.R.string.MassageToMasterReady)
                            )
                        }
                    )
                }
            }
        }
    }

    if (state.isFullScreenImageVisible) {
        FullScreenImage(
            imageResId = R.drawable.drawing,
            onDismiss = { viewModel.setFullScreenImageVisible(false) }
        )
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