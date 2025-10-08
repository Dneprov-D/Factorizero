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
import com.hfad.main.presentation.EmployeeTaskDetailsScreen
import com.hfad.main.presentation.MainEmployeeScreen
import com.hfad.main.presentation.employeepack.EditEmployeeScreen
import com.hfad.main.presentation.employeepack.EmployeeDetailsScreen
import com.hfad.main.presentation.masterpack.MasterMainScreen
import com.hfad.navigation.EmployeeTabsScreens
import com.hfad.navigation.NoArrowBackScreens
import com.hfad.profile.MasterProfileScreen
import com.hfad.navigation.Screen
import com.hfad.navigation.TopLevelScreens
import com.hfad.profile.EmployeeProfileScreen
import com.hfad.tasks.presentation.CreateNewTaskScreen
import com.hfad.tasks.presentation.EditTaskScreen
import com.hfad.tasks.presentation.TaskDetailsScreen
import com.hfad.tasks.presentation.TasksMasterScreen
import kotlin.reflect.KClass

//Master version
fun NavGraphBuilder.profileTabNavGraph(
) {
    composable<Screen.ProfileTabScreen> {
        MasterProfileScreen()
    }
}

fun NavGraphBuilder.employeeTabNavGraph(
    navController: NavHostController,
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
        composable<Screen.EditEmployeeScreen> {
            EditEmployeeScreen(
                onEdited = {
                    navController.navigateToNewRoot(Screen.MainMasterScreen)
                },
                onDeleted = {
                    navController.navigateToNewRoot(Screen.MainMasterScreen)
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
            TasksMasterScreen(
                onTaskClick = {
                    navController.navigate(
                        Screen.TaskDetailsScreen(
                            key = it.key,
                            title = it.title,
                            quantity = it.quantity
                        )
                    )
                },
                onFabClick = {
                    navController.navigate(
                        Screen.CreateNewTaskScreen
                    )
                }
            )
        }
        composable<Screen.CreateNewTaskScreen> {
            CreateNewTaskScreen(
                onTaskCreated = {
                    navController.navigateToNewRoot(Screen.TasksMasterScreen)
                }
            )
        }
        composable<Screen.TaskDetailsScreen> {
            TaskDetailsScreen(
                onEditTaskClick = {
                    navController.navigate(
                        Screen.EditTaskScreen(
                            key = it.key,
                            title = it.title,
                            quantity = it.quantity
                        )
                    )
                }
            )
        }
        composable<Screen.EditTaskScreen> {
            EditTaskScreen(
                onEdited = {
                    navController.navigateToNewRoot(Screen.TasksMasterScreen)
                },
                onDeleted = {
                    navController.navigateToNewRoot(Screen.TasksMasterScreen)
                }
            )
        }
    }
}
//Employee version
fun NavGraphBuilder.employeeMainTabNavGraph(
    navController: NavHostController
) {
    navigation<Screen.EmployeeMainTabScreen>(
        startDestination = Screen.MainEmployeeScreen
    ) {
        composable<Screen.MainEmployeeScreen> {
            MainEmployeeScreen(
                onTaskClick = {
                    navController.navigate(
                        Screen.EmployeeTaskDetailsScreen(
                            key = it.key,
                            title = it.title,
                            quantity = it.quantity
                        )
                    )
                }
            )
        }
        composable<Screen.EmployeeTaskDetailsScreen> {
            EmployeeTaskDetailsScreen()
        }
    }
}

fun NavGraphBuilder.employeeProfileTabNavGraph(
    navController: NavController
) {
    navigation<Screen.EmployeeProfileTabScreen>(
        startDestination = Screen.EmployeeProfileScreen
    ) {
        composable<Screen.EmployeeProfileScreen> {
            EmployeeProfileScreen(
            //TODO
            )
        }
    }
}

fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false

fun NavController.navigateToBottomNavigationDestination(
    bottomNavigationDestination: BottomNavigationDestination,
) {
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
        BottomNavigationDestination.EMPLOYEE_MAIN_TAB -> navigateToEmployeeMainTab(bottomNavigationNavOptions)
        BottomNavigationDestination.EMPLOYEE_PROFILE_TAB-> navigateToEmployeeProfileTab(bottomNavigationNavOptions)
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

fun NavController.navigateToEmployeeMainTab(navOptions: NavOptions) =
    navigate(route = Screen.MainEmployeeScreen, navOptions)

fun NavController.navigateToEmployeeProfileTab(navOptions: NavOptions) =
    navigate(route = Screen.EmployeeProfileScreen, navOptions)

fun NavController.navigateToEmployeeTab(navOptions: NavOptions) =
    navigate(route = Screen.EmployeeTabScreen, navOptions)

fun NavController.navigateToTasksTab(navOptions: NavOptions) =
    navigate(route = Screen.TasksTabScreen, navOptions)

fun NavController.navigateToProfileTab(navOptions: NavOptions) =
    navigate(route = Screen.ProfileTabScreen, navOptions)

fun NavController.navigateToNewRoot(destination: Any) {
    val route = when (destination) {
        is String -> destination
        is Screen -> destination::class.qualifiedName ?: destination.toString()
        else -> destination.toString()
    }

    navigate(route) {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }
}
