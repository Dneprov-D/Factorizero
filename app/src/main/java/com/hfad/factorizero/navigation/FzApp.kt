package com.hfad.factorizero.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
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
                Surface(
                    shape = RoundedCornerShape(bottomEnd = 40.dp),
                    color = LightColorScheme.tertiary,
                    elevation = 0.dp
                ) {
                    TopAppBar(
                        modifier = Modifier
                            .height(80.dp),
                        title = {
                            Box(
                                modifier = Modifier
                                    .padding(start = 130.dp, top = 25.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.app_name),
                                    modifier = Modifier.align(Alignment.BottomEnd)
                                )
                            }
                        },
                        navigationIcon = {
                            if (shouldShowArrowBack(currentDestination))
                                IconButton(
                                    onClick = { navController.navigateUp() },
                                    modifier = Modifier.padding(top = 25.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.FzIcons.ArrowBack,
                                        contentDescription = null
                                    )
                                }
                        },
                        backgroundColor = Color.Transparent,
                        contentColor = Color.White,
                        elevation = 0.dp
                    )
                }
        },
        bottomBar = {
            if (shouldShowBottomBar(currentDestination) && shouldShowEmployeeTab(currentDestination)) {
                NavigationBar(
                    modifier = Modifier
                        .height(100.dp)
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