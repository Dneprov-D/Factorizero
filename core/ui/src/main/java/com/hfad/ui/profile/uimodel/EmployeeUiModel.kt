package com.hfad.ui.profile.uimodel

import com.hfad.model.Employee

data class EmployeeUiModel(
    val key: String,
    val name: String,
    val surname: String,
    val jobTitle: String
)

fun Employee.toUiModel(): EmployeeUiModel {
    return EmployeeUiModel(
        key = key,
        name = name,
        surname = surname,
        jobTitle = jobTitle
    )
}