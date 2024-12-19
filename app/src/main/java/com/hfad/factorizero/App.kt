package com.hfad.factorizero

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hfad.common.compose.navigateToNewRoot
import com.hfad.navigation.Screen

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}