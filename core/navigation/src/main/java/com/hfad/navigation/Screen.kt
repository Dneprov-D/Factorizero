package com.hfad.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen() {

    @Serializable
    data object LoginScreen : Screen()

    @Serializable
    data object LoginAsMasterScreen : Screen()

    @Serializable
    data object LoginAsEmployeeScreen : Screen()

    @Serializable
    data object MainMasterScreen : Screen()

    @Serializable
    data object MainEmployeeScreen : Screen()

    @Serializable
    data object TasksMasterScreen : Screen()

    @Serializable
    data object CreateNewTaskScreen : Screen()

    @Serializable
    data object CreateNewEmployeeScreen : Screen()

    @Serializable
    data object CreateNewMasterScreen : Screen()

    @Serializable
    data object EmployeeTabScreen : Screen()

    @Serializable
    data object TasksTabScreen : Screen()

    @Serializable
    data object ProfileTabScreen : Screen()

    @Serializable
    data object EmployeeProfileScreen : Screen()

    @Serializable
    data object EmployeeProfileTabScreen : Screen()

    @Serializable
    data object EmployeeMainTabScreen : Screen()

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
    data class EditTaskScreen(
        val key: String,
        val title: String,
        val quantity: String,
        val doneCount: Int
    ) : Screen()

    @Serializable
    data class TaskDetailsScreen(
        val key: String,
        val title: String,
        val quantity: String,
        val doneCount: Int = 0
    ) : Screen()

    @Serializable
    data class EmployeeTaskDetailsScreen(
        val key: String,
        val title: String,
        val quantity: String
    ) : Screen()

    @Serializable
    data class MainScreenDataObject(
        val uid: String = "",
        val email: String = ""
    )
}


