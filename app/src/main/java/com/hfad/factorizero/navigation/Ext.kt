package com.hfad.factorizero.navigation

import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
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

fun NavController.navigateToBottomNavigationDestination(bottomNavigationDestination: BottomNavigationDestination) {
    val bottomNavigationNavOptions = navOptions {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
    when (bottomNavigationDestination) {
        BottomNavigationDestination.EMPLOYEE_TAB -> navigateToEmployeeTab(bottomNavigationNavOptions)
        BottomNavigationDestination.TASKS_TAB -> navigateToTasksTab(bottomNavigationNavOptions)
    }
}

fun shouldShowBottomBar(currentDestination: NavDestination?) =
    currentDestination?.let { destination ->
        TopLevelScreens.entries.none { screen ->
            destination.hasRoute(screen.route)
        }
    } == true

fun NavController.navigateToEmployeeTab(navOptions: NavOptions) =
    navigate(route = Screen.EmployeeTabScreen, navOptions)

fun NavController.navigateToTasksTab(navOptions: NavOptions) =
    navigate(route = Screen.TasksTabScreen, navOptions)

