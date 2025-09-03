package com.hfad.factorizero.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import com.hfad.authorization.presentation.LoginScreen
import com.hfad.authorization.presentation.employee.LoginAsEmployeeScreen
import com.hfad.authorization.presentation.master.LoginAsMasterScreen
import com.hfad.common.compose.navigateToNewRoot
import com.hfad.main.presentation.employeepack.CreateNewEmployeeScreen
import com.hfad.main.presentation.masterpack.CreateNewMasterScreen
import com.hfad.navigation.Screen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.LoginScreen,
    modifier: Modifier
) {

    LaunchedEffect(Unit) {
        Log.e("pop", "navGraph LaunchedEffect")
        Firebase.auth.addAuthStateListener {
            Log.e("pop", "auth ${it.currentUser}")
            if (it.currentUser != null) {
                navController.navigateToNewRoot(Screen.MainMasterScreen)
            } else if (it.currentUser == null) {
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
            LoginScreen(
                onMasterClick = {
                    navController.navigate(Screen.LoginAsMasterScreen)
                },
                onEmployeeClick = {
                    navController.navigate(Screen.LoginAsEmployeeScreen)
                }
            )
        }

        composable<Screen.LoginAsMasterScreen> {
            LoginAsMasterScreen(
                onNavigateToMainScreen = {
                    navController.navigateToNewRoot(Screen.MainMasterScreen)
                },
                onRegisterMasterClick = {
                    navController.navigate(Screen.CreateNewMasterScreen)
                }
            )
        }

        composable<Screen.LoginAsEmployeeScreen> {
            LoginAsEmployeeScreen(
                onNavigateToMainScreen = {
                    navController.navigateToNewRoot(Screen.MainMasterScreen)
                },
                onRegisterEmployeeClick = {
                    navController.navigate(Screen.CreateNewEmployeeScreen)
                }
            )
        }

        composable<Screen.CreateNewEmployeeScreen> {
            CreateNewEmployeeScreen(
                onRegistered = {
                    navController.navigateToNewRoot(Screen.MainMasterScreen)
                }
            )
        }

        composable<Screen.CreateNewMasterScreen> {
            CreateNewMasterScreen(
                onRegistered = {
                    navController.navigateToNewRoot(Screen.MainMasterScreen)
                }
            )
        }

        employeeTabNavGraph(navController)
        tasksTabNavGraph(navController)
        profileTabNavGraph()
    }
}
