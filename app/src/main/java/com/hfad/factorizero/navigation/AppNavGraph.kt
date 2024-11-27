package com.hfad.factorizero.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hfad.main.presentation.CreateNewEmployee
import com.hfad.main.presentation.DetailsOFEmployee
import com.hfad.main.presentation.MasterMainScreen
import com.hfad.navigation.Screen
import com.hfad.tasks.presentation.CreateNewTask
import com.hfad.tasks.presentation.DetailsOFTask
import com.hfad.tasks.presentation.TasksMaster

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.MainMasterScreen,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable<Screen.MainMasterScreen> {
            MasterMainScreen(navController)
        }

        composable<Screen.TasksTab> {
            TasksMaster(navController)
        }

        composable<Screen.CreateNewTask> {
            CreateNewTask()
        }

        composable<Screen.DetailsOfTask> {
            DetailsOFTask()
        }

        composable<Screen.CreateNewEmployee> {
            CreateNewEmployee()
        }

        composable<Screen.DetailsOfEmployee> {
            DetailsOFEmployee(navController)
        }
    }
}