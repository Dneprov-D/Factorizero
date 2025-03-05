package com.hfad.factorizero.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hfad.authorization.presentation.LoginScreen
import com.hfad.common.compose.navigateToNewRoot
import com.hfad.navigation.Screen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.LoginScreen,
    modifier: Modifier
) {
    LaunchedEffect(Unit) {
        Log.e("pop", "navGraph LaunchedEffect")
        Firebase.auth.addAuthStateListener {
            Log.e("pop", "auth ${it.currentUser}")
            if (it.currentUser != null) {
                navController.navigateToNewRoot(Screen.MainMasterScreen)
            } else {
                navController.navigateToNewRoot(Screen.LoginScreen)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable<Screen.LoginScreen> {
            LoginScreen {
                navController.navigateToNewRoot(Screen.MainMasterScreen)
            }
        }
        employeeTabNavGraph(navController)
        tasksTabNavGraph(navController)
        profileTabNavGraph()
    }
}

// TODO перевызывается AppNavGraph при вызове navigateUp() из рутового экрана а ожидается сворачивание приложения (не рекомпозиция а с нуля)
// TODO происходит при первом вызове navigateUp() далее игнорируется