package com.hfad.factorizero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hfad.authorization.presentation.MainLoginScreen
import com.hfad.designsystem.components.theme.FactorizeroTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FactorizeroTheme {
                MainLoginScreen()
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable