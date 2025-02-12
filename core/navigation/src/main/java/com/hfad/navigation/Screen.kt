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
    data class EmployeeDetailsScreen(
        val key: String,
        val name: String,
        val surname: String,
        val jobTitle: String
    ) : Screen()

    @Serializable
    data class EditEmployeeScreen(
        val key: String,
        val name: String,
        val surname: String,
        val jobTitle: String
    ) : Screen()

    @Serializable
    data object CreateNewEmployeeScreen : Screen()

    @Serializable
    data object TaskDetailsScreen : Screen()

    @Serializable
    data object EmployeeTabScreen : Screen()

    @Serializable
    data object TasksTabScreen : Screen()

    @Serializable
    data object ProfileTabScreen : Screen()

    @Serializable
    data class MainScreenDataObject(
        val uid: String = "",
        val email: String = ""
    )
}

