package com.hfad.factorizero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hfad.designsystem.components.theme.FactorizeroTheme
import com.hfad.factorizero.navigation.FzApp



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

