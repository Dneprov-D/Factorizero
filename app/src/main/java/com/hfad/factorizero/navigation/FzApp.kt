package com.hfad.factorizero.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hfad.navigation.BottomBarMaster
import com.hfad.navigation.Screen
import com.hfad.navigation.TopLevelScreens

@Composable
fun FzApp() {
    
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    Scaffold(
        bottomBar = {
            if (
                currentDestination?.let { destination ->
                    TopLevelScreens.entries.none { screen ->
                        destination.hasRoute(screen.route)
                    }
                } == true
            )
            BottomBarMaster(onItemSelected = { index ->
                when (index) {
                    0 -> navController.navigate(Screen.EmployeeTabScreen)
                    1 -> navController.navigate(Screen.TasksTabScreen)
                }
            })
        }
    ) { innerPadding ->
        AppNavGraph(
            navController = navController,
            startDestination = Screen.LoginScreen,
            modifier = Modifier.padding(innerPadding)
        )
    }
}