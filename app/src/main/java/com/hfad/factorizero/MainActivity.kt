package com.hfad.factorizero

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hfad.designsystem.components.theme.FactorizeroTheme
import com.hfad.factorizero.navigation.FzApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("pop ","MainActivity")
        setContent {
            FactorizeroTheme {
                FzApp()
            }
        }
    }
}