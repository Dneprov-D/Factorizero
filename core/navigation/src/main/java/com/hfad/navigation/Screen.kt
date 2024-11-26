package com.hfad.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object MainMasterScreen : Screen()

    @Serializable
    data object TasksTab : Screen()

    @Serializable
    data object CreateNewTask : Screen()

    @Serializable
    data object DetailsOfEmployee : Screen()

}