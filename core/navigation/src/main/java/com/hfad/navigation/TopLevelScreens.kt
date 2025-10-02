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
    PROFILE_SCREEN(route = Screen.ProfileTabScreen::class),
    EMPLOYEE_MAIN_TAB(route = Screen.MainEmployeeScreen::class),
    EMPLOYEE_PROFILE_TAB(route = Screen.EmployeeProfileScreen::class)
}

enum class EmployeeTabsScreens(val route: KClass<*>) {
    EMPLOYEE_MAIN_TAB(route = Screen.MainEmployeeScreen::class),
    EMPLOYEE_PROFILE_TAB(route = Screen.EmployeeProfileScreen::class)
}

enum class MasterTabsScreens(val route: KClass<*>) {
    EMPLOYEE_TAB(route = Screen.EmployeeTabScreen::class),
    TASKS_TAB(route = Screen.TasksTabScreen::class),
    PROFILE_TAB(route = Screen.ProfileTabScreen::class),
    MAIN_MASTER(route = Screen.MainMasterScreen::class),
}