package com.hfad.data.repository

import android.util.Log
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.hfad.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class LoginRepository @Inject constructor(
    private val auth: FirebaseAuth
) {
    fun createAnAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.e("LogLogin", "CreateAnAccount - Successful!")
                } else {
                    Log.e("LogLogin", "CreateAnAccount - Failed!")
                }
            }
    }

    fun signIn(email: String, password: String) {

    }
}
