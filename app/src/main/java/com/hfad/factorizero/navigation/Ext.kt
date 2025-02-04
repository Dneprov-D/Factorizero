package com.hfad.factorizero.navigation

import android.util.Log
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
import androidx.navigation.toRoute
import com.hfad.main.presentation.employeepack.CreateNewEmployeeScreen
import com.hfad.main.presentation.employeepack.EmployeeDetailsScreen
import com.hfad.main.presentation.masterpack.MasterMainScreen
import com.hfad.main.presentation.masterpack.MasterMainViewModel
import com.hfad.model.Employee
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
                navController,
                viewModel = MasterMainViewModel(),
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

//        @HiltViewModel
//        class CodeInputViewModel @Inject constructor(
//            private val requestTokenUseCase: RequestTokenUseCase,
//            private val setTokenUseCase: SetTokenUseCase,
//            stateHandle: SavedStateHandle
//        ) : BaseViewModel<CodeInputEvent>() {
//
//            private val email: String = stateHandle.toRoute<CodeInputScreenRoute>().email

        composable<Screen.EmployeeDetailsScreen> {
            val args = it.toRoute<Screen.EmployeeDetailsScreen>()
            EmployeeDetailsScreen(
                employee = Employee(
                    key = args.key,
                    name = args.name,
                    surname = args.surname,
                    jobTitle = args.jobTitle
                )
            )
        }

        composable<Screen.CreateNewEmployeeScreen> {
            CreateNewEmployeeScreen()
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
        BottomNavigationDestination.PROFILE_TAB -> navigateToProfileTab(bottomNavigationNavOptions)
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

fun NavController.navigateToProfileTab(navOptions: NavOptions) =
    navigate(route = Screen.ProfileTabScreen, navOptions)