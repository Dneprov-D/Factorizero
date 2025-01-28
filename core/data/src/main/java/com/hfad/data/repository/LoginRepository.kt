package com.hfad.data.repository

import android.util.Log
import androidx.navigation.NavHostController
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hfad.common.compose.Employee
import com.hfad.common.compose.navigateToNewRoot
import com.hfad.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val auth: FirebaseAuth
) {

    fun createAnAccount(
        email: String,
        password: String,
        onSignUpSuccess: (Screen.MainScreenDataObject) -> Unit,
        onSignUpFailure: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            onSignUpFailure("Login and Password cannot be empty")
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSignUpSuccess(
                        Screen.MainScreenDataObject(
                            it.result.user?.uid!!,
                            it.result.user?.email!!
                        )
                    )
                }
            }
            .addOnFailureListener {
                onSignUpFailure(it.message ?: "Произошла ошибка регистрации")
            }
    }

    fun createAnEmployee(
        email: String,
        password: String,
        name: String,
        surname: String,
        jobTitle: String,
        onRegisterSuccess: (Screen.MainScreenDataObject) -> Unit,
        onRegisterFailure: (String) -> Unit
    ) {
        val fireStore = Firebase.firestore
        val db = fireStore.collection("stuff")
        val key = db.document().id

        if (email.isBlank() || password.isBlank()) {
            onRegisterFailure("Логин и пароль не могут быть пустыми.")
            return
        }

        if (name.isBlank() || surname.isBlank()) {
            onRegisterFailure("Имя и фамилия не могут быть пустыми.")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                onRegisterSuccess(
                    Screen.MainScreenDataObject(
                        it.result.user?.uid!!,
                        it.result.user?.email!!
                    )
                )
                fireStore.collection("stuff")
                    .document("employee").set(
                        Employee(
                            key = key,
                            name = name,
                            surname = surname,
                            jobTitle = jobTitle
                        )
                    )
            }
            .addOnFailureListener {
                onRegisterFailure(it.message ?: "Произошла ошибка регистрации")
            }
    }

    fun signIn(
        email: String,
        password: String,
        onSignInSuccess: (Screen.MainScreenDataObject) -> Unit,
        onSignInFailure: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            onSignInFailure("Login and Password cannot be empty")
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSignInSuccess(
                        Screen.MainScreenDataObject(
                            it.result.user?.uid!!,
                            it.result.user?.email!!
                        )
                    )
                }
            }.addOnFailureListener {
                onSignInFailure(it.message ?: "Произошла ошибка при входе")
            }
    }

    fun signOut() {
        auth.signOut()
        Log.e("LogLogin", "Sign Out")
    }

    fun deleteAccount(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser?.reauthenticate(credential)?.addOnCompleteListener {
            if (it.isSuccessful) {
                auth.currentUser?.delete()?.addOnCompleteListener {
                    if (it.isSuccessful) {
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