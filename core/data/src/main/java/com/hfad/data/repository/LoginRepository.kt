package com.hfad.data.repository

import android.util.Log
import androidx.navigation.NavHostController
import com.google.firebase.auth.EmailAuthProvider
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
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.e("LogLogin", "Sign In - Successful!")
                } else {
                    Log.e("LogLogin", "Sign In - Failed!")
                }
            }
    }

    fun signOut() {
        auth.signOut()
        Log.e("LogLogin", "Sign Out")
    }

    fun deleteAccount(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser?.reauthenticate(credential)?.addOnCompleteListener {
            if(it.isSuccessful) {
                auth.currentUser?.delete()?.addOnCompleteListener {
                    if(it.isSuccessful) {
                        Log.e("LogLogin", "Account was deleted!")
                    } else {
                        Log.e("LogLogin", "Failure delete account!")
                    }
                }
            } else {
                Log.e("LogLogin", "Failure reauthenticate!")
            }
        }
        Log.e("LogLogin", "Account already was deleted!")
    }
}
