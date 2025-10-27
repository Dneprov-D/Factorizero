package com.hfad.model

data class WorkTask(
    val key: String = "",
    val imageUrl: String = "",
    val title: String = "",
    val quantity: String = "",
    val doneCount: Int = 0,
    val done: Boolean = false
)