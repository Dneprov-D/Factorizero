package com.hfad.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen() {
    @Serializable
    data object LoginScreen : Screen()

    @Serializable
    data object MainMasterScreen : Screen()

    @Serializable
    data object TasksMasterScreen : Screen()

    @Serializable
    data object CreateNewTaskScreen : Screen()

    @Serializable
    data object EmployeeDetails : Screen()

    @Serializable
    data object CreateNewEmployeeScreen : Screen()

    @Serializable
    data object TaskDetails : Screen()

    @Serializable
    data class MainScreenDataObject(
        val uid: String = "",
        val email: String = ""
    )
}