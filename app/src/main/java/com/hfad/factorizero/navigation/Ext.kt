package com.hfad.factorizero.navigation

import androidx.navigation.NavDestination.Companion.hasRoute
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.hfad.main.presentation.CreateNewEmployee
import com.hfad.main.presentation.EmployeeDetails
import com.hfad.main.presentation.MasterMainScreen
import com.hfad.navigation.Screen
import com.hfad.navigation.TopLevelScreens
import com.hfad.tasks.presentation.CreateNewTaskScreen
import com.hfad.tasks.presentation.TaskDetailsScreen
import com.hfad.tasks.presentation.TasksMasterScreen
import kotlin.reflect.KClass

fun NavGraphBuilder.employeeTabNavGraph(
    navController: NavHostController
) {
    navigation<Screen.EmployeeTabScreen>(
        startDestination = Screen.MainMasterScreen
    ) {
        composable<Screen.MainMasterScreen> {
            MasterMainScreen(navController)
        }
        composable<Screen.EmployeeDetailsScreen> {
            EmployeeDetails(navController)
        }
        composable<Screen.CreateNewEmployeeScreen> {
            CreateNewEmployee()
        }
    }
}

fun NavGraphBuilder.tasksTabNavGraph(
    navController: NavHostController
) {
    navigation<Screen.TasksTabScreen>(
        startDestination = Screen.TasksMasterScreen
    ) {
        composable<Screen.TasksMasterScreen> {
            TasksMasterScreen(navController)
        }
        composable<Screen.CreateNewTaskScreen> {
            CreateNewTaskScreen()
        }
        composable<Screen.TaskDetailsScreen> {
            TaskDetailsScreen()
        }
    }
}

fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false

fun shouldShowBottomBar(currentDestination: NavDestination?) =
    currentDestination?.let { destination ->
        TopLevelScreens.entries.none { screen ->
            destination.hasRoute(screen.route)
        }
    } == true