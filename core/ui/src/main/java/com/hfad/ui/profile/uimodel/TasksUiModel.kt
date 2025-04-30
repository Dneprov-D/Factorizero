package com.hfad.ui.profile.uimodel

import com.hfad.model.Employee
import com.hfad.model.WorkTask

data class TaskUiModel(
    val key: String,
    val title: String,
    val quantity: String
)

fun WorkTask.toUiModel(): TaskUiModel {
    return TaskUiModel(
        key = key,
        title = title,
        quantity = quantity
    )
}