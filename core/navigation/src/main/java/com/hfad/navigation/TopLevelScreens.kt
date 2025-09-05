package com.hfad.navigation

import kotlin.reflect.KClass

enum class TopLevelScreens(val route: KClass<*>) {
    LOGIN_SCREEN(route = Screen.LoginScreen::class),
    LOGIN_MASTER(route = Screen.LoginAsMasterScreen::class),
    LOGIN_EMPLOYEE(route = Screen.LoginAsEmployeeScreen::class),
    REGISTER_SCREEN(route = Screen.CreateNewEmployeeScreen::class),
    REGISTER_MASTER_SCREEN(route = Screen.CreateNewMasterScreen::class)
}

enum class NoArrowBackScreens(val route: KClass<*>) {
    MAIN_SCREEN(route = Screen.MainMasterScreen::class),
    TASKS_SCREEN(route = Screen.TasksMasterScreen::class),
    PROFILE_SCREEN(route = Screen.ProfileTabScreen::class)
}

enum class NoEmployeeTabScreens(val route: KClass<*>) {

}
