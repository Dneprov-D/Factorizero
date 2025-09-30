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
import com.google.firebase.firestore.FirebaseFirestore
import com.hfad.authorization.presentation.LoginScreen
import com.hfad.authorization.presentation.employee.LoginAsEmployeeScreen
import com.hfad.authorization.presentation.master.LoginAsMasterScreen
import com.hfad.common.compose.navigateToNewRoot
import com.hfad.main.presentation.CreateNewEmployeeScreen
import com.hfad.main.presentation.masterpack.CreateNewMasterScreen
import com.hfad.navigation.Screen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.LoginScreen,
    modifier: Modifier
) {

    LaunchedEffect(Unit) {
        Firebase.auth.addAuthStateListener { auth ->
            val user = auth.currentUser
            if (user != null) {
                val uid = user.uid
                val db = FirebaseFirestore.getInstance()
                
                db.collection("master")
                    .whereEqualTo("uid", uid)
                    .get()
                    .addOnSuccessListener { masterDocuments ->
                        if (!masterDocuments.isEmpty) {
                            navController.navigateToNewRoot(Screen.MainMasterScreen)
                        } else {
                            db.collection("staff")
                                .whereEqualTo("uid", uid)
                                .get()
                                .addOnSuccessListener { staffDocuments ->
                                    if (!staffDocuments.isEmpty) {
                                        navController.navigateToNewRoot(Screen.MainEmployeeScreen)
                                    } else {
                                        navController.navigateToNewRoot(Screen.LoginScreen)
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    navController.navigateToNewRoot(Screen.LoginScreen)
                                }
                        }
                    }
                    .addOnFailureListener { exception ->
                        navController.navigateToNewRoot(Screen.LoginScreen)
                    }
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
                    navController.navigateToNewRoot(Screen.MainEmployeeScreen)
                },
                onRegisterEmployeeClick = {
                    navController.navigate(Screen.CreateNewEmployeeScreen)
                }
            )
        }

        composable<Screen.CreateNewEmployeeScreen> {
            CreateNewEmployeeScreen(
                onRegistered = {
                    navController.navigateToNewRoot(Screen.MainEmployeeScreen)
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

        // Master ver.
        employeeTabNavGraph(navController)
        tasksTabNavGraph(navController)
        profileTabNavGraph()

        // Employee ver.
        employeeMainTabNavGraph(navController)
        employeeProfileTabNavGraph(navController)
    }
}