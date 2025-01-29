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
import com.hfad.navigation.Screen

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
        employeeTabNavGraph(navController)
        tasksTabNavGraph(navController)
        profileTabNavGraph(navController)
    }
}