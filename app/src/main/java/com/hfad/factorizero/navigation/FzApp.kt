package com.hfad.factorizero.navigation

import Icons.FzIcons
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hfad.designsystem.components.theme.LightColorScheme
import com.hfad.main.presentation.MasterMainScreen
import com.hfad.navigation.BottomBarMaster
import com.hfad.navigation.Screen

@Composable
fun FzApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBarMaster(onItemSelected = { index ->
                when (index) {
                    0 -> navController.navigate(Screen.MainMasterScreen)
                    1 -> navController.navigate(Screen.TasksMasterScreen)
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