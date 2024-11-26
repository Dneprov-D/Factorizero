package com.hfad.factorizero

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hfad.authorization.presentation.MainLoginScreen
import com.hfad.designsystem.components.theme.FactorizeroTheme
import com.hfad.factorizero.navigation.FzApp
import com.hfad.main.presentation.MasterMainScreen
import com.hfad.tasks.presentation.CreateNewTasks
import com.hfad.ui.profile.SelectedEmployeeCard


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FactorizeroTheme {
                FzApp()
            }
        }
    }
}

