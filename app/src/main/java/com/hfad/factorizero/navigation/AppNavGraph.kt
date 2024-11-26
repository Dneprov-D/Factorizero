package com.hfad.factorizero.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hfad.main.presentation.MasterMainScreen
import com.hfad.navigation.Screen
import com.hfad.tasks.presentation.TasksMaster
import com.hfad.ui.profile.TaskCard

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.MainMasterScreen
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable<Screen.MainMasterScreen> {
            MasterMainScreen(navController)
        }

        composable<Screen.TasksTab> {
            TasksMaster(navController)
        }
    }
}