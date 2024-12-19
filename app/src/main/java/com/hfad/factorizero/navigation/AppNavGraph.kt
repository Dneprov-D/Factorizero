package com.hfad.factorizero.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hfad.authorization.presentation.LoginScreen
import com.hfad.common.compose.navigateToNewRoot
import com.hfad.main.presentation.CreateNewEmployee
import com.hfad.main.presentation.EmployeeDetails
import com.hfad.main.presentation.MasterMainScreen
import com.hfad.navigation.Screen
import com.hfad.tasks.presentation.CreateNewTaskScreen
import com.hfad.tasks.presentation.TaskDetailsScreen
import com.hfad.tasks.presentation.TasksMasterScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.LoginScreen,
    modifier: Modifier
) {
    LaunchedEffect(Unit) {
        Firebase.auth.addAuthStateListener {
            if (it.currentUser != null) {
                navController.navigateToNewRoot(Screen.MainMasterScreen)
            } else {
                navController.navigateToNewRoot(Screen.LoginScreen)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable<Screen.LoginScreen> {
            LoginScreen {
                navController.navigateToNewRoot(Screen.MainMasterScreen)
            }
        }

        composable<Screen.MainMasterScreen> {
            MasterMainScreen(navController)
        }

        composable<Screen.TasksMasterScreen> {
            TasksMasterScreen(navController)
        }

        composable<Screen.CreateNewTaskScreen> {
            CreateNewTaskScreen()
        }

        composable<Screen.TaskDetails> {
            TaskDetailsScreen()
        }

        composable<Screen.CreateNewEmployeeScreen> {
            CreateNewEmployee()
        }

        composable<Screen.EmployeeDetails> {
            EmployeeDetails(navController)
        }
    }
}