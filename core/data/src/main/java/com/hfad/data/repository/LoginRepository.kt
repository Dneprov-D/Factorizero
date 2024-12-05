package com.hfad.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginRepository @Inject constructor(private val auth: FirebaseAuth) {
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
}
