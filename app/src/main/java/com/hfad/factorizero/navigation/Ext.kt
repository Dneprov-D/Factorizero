package com.hfad.factorizero.navigation

import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.hfad.main.presentation.employeepack.CreateNewEmployeeScreen
import com.hfad.main.presentation.employeepack.EditEmployeeScreen
import com.hfad.main.presentation.employeepack.EmployeeDetailsScreen
import com.hfad.main.presentation.masterpack.MasterMainScreen
import com.hfad.navigation.NoArrowBackScreens
import com.hfad.profile.MasterProfileScreen
import com.hfad.navigation.Screen
import com.hfad.navigation.TopLevelScreens
import com.hfad.tasks.presentation.CreateNewTaskScreen
import com.hfad.tasks.presentation.TaskDetailsScreen
import com.hfad.tasks.presentation.TasksMasterScreen
import kotlin.reflect.KClass

fun NavGraphBuilder.profileTabNavGraph(
) {
    composable<Screen.ProfileTabScreen> {
        MasterProfileScreen()
    }
}

fun NavGraphBuilder.employeeTabNavGraph(
    navController: NavHostController
) {
    navigation<Screen.EmployeeTabScreen>(
        startDestination = Screen.MainMasterScreen
    ) {

        composable<Screen.MainMasterScreen> {
            MasterMainScreen(
                onEmployeeClick = {
                    navController.navigate(
                        Screen.EmployeeDetailsScreen(
                            key = it.key,
                            name = it.name,
                            surname = it.surname,
                            jobTitle = it.jobTitle
                        )
                    )
                },
                onFabClick = {
                    navController.navigate(route = Screen.CreateNewEmployeeScreen)
                }
            )
        }

        composable<Screen.EmployeeDetailsScreen> {
            EmployeeDetailsScreen(
                onEditClick = {
                    navController.navigate(
                        Screen.EditEmployeeScreen(
                            key = it.key,
                            name = it.name,
                            surname = it.surname,
                            jobTitle = it.jobTitle
                        )
                    )
                }
            )
        }

        composable<Screen.CreateNewEmployeeScreen> {
            CreateNewEmployeeScreen(
                onRegistered = {
                    navController.navigate(Screen.MainMasterScreen)
                }
            )
        }

        composable<Screen.EditEmployeeScreen> {
            EditEmployeeScreen(
                onEdited = {
                    navController.popBackStack()
                },
                onDeleted = {
                    navController.popBackStack(
                        route = Screen.MainMasterScreen,
                        inclusive = false
                    )
                }
            )
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
        popUpTo(0) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

    when (bottomNavigationDestination) {
        BottomNavigationDestination.EMPLOYEE_TAB -> navigateToEmployeeTab(bottomNavigationNavOptions)
        BottomNavigationDestination.TASKS_TAB -> navigateToTasksTab(bottomNavigationNavOptions)
        BottomNavigationDestination.PROFILE_TAB -> navigateToProfileTab(bottomNavigationNavOptions)
    }
}

fun shouldShowBottomBar(currentDestination: NavDestination?) =
    currentDestination?.let { destination ->
        TopLevelScreens.entries.none { screen ->
            destination.hasRoute(screen.route)
        }
    } == true

fun shouldShowTopBar(currentDestination: NavDestination?) =
    currentDestination?.let { destination ->
        TopLevelScreens.entries.none { screen ->
            destination.hasRoute(screen.route)
        }
    } == true

fun shouldShowArrowBack(currentDestination: NavDestination?) =
    currentDestination?.let { destination ->
        NoArrowBackScreens.entries.none { screen ->
            destination.hasRoute(screen.route)
        }
    } == true

fun NavController.navigateToEmployeeTab(navOptions: NavOptions) =
    navigate(route = Screen.EmployeeTabScreen, navOptions)

fun NavController.navigateToTasksTab(navOptions: NavOptions) =
    navigate(route = Screen.TasksTabScreen, navOptions)

fun NavController.navigateToProfileTab(navOptions: NavOptions) =
    navigate(route = Screen.ProfileTabScreen, navOptions)