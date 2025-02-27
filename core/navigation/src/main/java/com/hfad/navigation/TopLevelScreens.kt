package com.hfad.navigation

import kotlin.reflect.KClass

enum class TopLevelScreens(val route: KClass<*>) {
    LOGIN_SCREEN(route = Screen.LoginScreen::class)
}

enum class NoArrowBackScreens(val route: KClass<*>) {
    MAIN_SCREEN(route = Screen.MainMasterScreen::class),
    TASK_SCREEN(route = Screen.TasksTabScreen::class),
    PROFILE_SCREEN(route = Screen.ProfileTabScreen::class)
}