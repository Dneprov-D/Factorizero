package com.hfad.factorizero.navigation

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hfad.designsystem.components.theme.LightColorScheme
import com.hfad.factorizero.R
import com.hfad.navigation.Screen

@Composable
fun FzApp() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val items: List<BottomNavigationDestination> = BottomNavigationDestination.entries

    Scaffold(
        topBar = {
            if (shouldShowTopBar(currentDestination))
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                navigationIcon = {
                    if (shouldShowArrowBack(currentDestination))
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.FzIcons.ArrowBack,
                                contentDescription = null
                            )
                        }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            )
        },
        bottomBar = {
            if (shouldShowBottomBar(currentDestination)) {
                NavigationBar(
                    modifier = Modifier.height(56.dp)
                ) {
                    items.forEach { destination ->
                        val selected = currentDestination.isRouteInHierarchy(destination.route)
                        BottomNavigationItem(
                            selected = selected,
                            onClick = {
                                navController.navigateToBottomNavigationDestination(destination)
                                Log.e("destination", "destination = $destination")
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