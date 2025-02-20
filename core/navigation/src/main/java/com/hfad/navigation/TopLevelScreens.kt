package com.hfad.navigation

import kotlin.reflect.KClass

enum class TopLevelScreens(val route: KClass<*>) {
    LOGIN_SCREEN(route = Screen.LoginScreen::class)
}

enum class MainScreen(val route: KClass<*>) {
    MAIN_SCREEN(route = Screen.MainMasterScreen::class)
}