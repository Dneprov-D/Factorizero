package com.hfad.ui.profile.uimodel

import com.hfad.model.Employee
import com.hfad.model.WorkTask
import kotlin.Boolean

data class TaskUiModel(
    val key: String,
    val title: String,
    val quantity: String,
    val doneCount: Int,
    val done: Boolean
)

fun WorkTask.toUiModel(): TaskUiModel {
    return TaskUiModel(
        key = key,
        title = title,
        quantity = quantity,
        doneCount = doneCount,
        done = done
    )
}