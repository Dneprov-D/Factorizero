package com.hfad.factorizero.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hfad.main.presentation.MasterMainScreen
import com.hfad.navigation.Screen

@Composable
fun FzApp() {
    val navController = rememberNavController()
    AppNavGraph(
        navController = navController,
        startDestination = Screen.MainMasterScreen
    )
}