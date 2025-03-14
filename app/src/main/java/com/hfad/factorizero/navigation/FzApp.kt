package com.hfad.factorizero.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hfad.designsystem.components.theme.LightColorScheme
import com.hfad.navigation.Screen

@Composable
fun FzApp() {

    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val items: List<BottomNavigationDestination> = BottomNavigationDestination.entries

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar(currentDestination)) {
                NavigationBar(
                    modifier = Modifier
                        .height(56.dp)
                ) {
                    items.forEach { destination ->
                        val selected = currentDestination
                            .isRouteInHierarchy(destination.route)
                        BottomNavigationItem(
                            selected = selected,
                            onClick = {
                                navController.navigateToBottomNavigationDestination(
                                    destination
                                )
                            },
                            icon = {
                                Icon(destination.icon, contentDescription = null)
                            },
                            label = {
                                Text(text = stringResource(id = destination.titleResId))
                            },
                            selectedContentColor = Color.Black,
                            unselectedContentColor = LightColorScheme.onTertiary
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AppNavGraph(
            navController = navController,
            startDestination = Screen.LoginScreen,
            modifier = Modifier.padding(innerPadding)
        )
    }
}