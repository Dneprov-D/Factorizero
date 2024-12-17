package com.hfad.factorizero.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.hfad.authorization.presentation.LoginScreen
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
    startDestination: Screen = Screen.LoginScreen,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable<Screen.LoginScreen> {
            LoginScreen {
                navController.navigate(
                    route = Screen.MainMasterScreen
                )
            }
        }

        composable<Screen.MainMasterScreen> { navEntry ->
            val navData = navEntry.toRoute<Screen.MainScreenDataObject>()
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