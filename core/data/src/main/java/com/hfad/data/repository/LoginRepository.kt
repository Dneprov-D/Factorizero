package com.hfad.data.repository

import android.util.Log
import androidx.navigation.NavHostController
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hfad.common.compose.navigateToNewRoot
import com.hfad.model.Employee
import com.hfad.navigation.Screen
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
        employee: Employee,
        email: String,
        password: String,
        name: String,
        surname: String,
        jobTitle: String,
        onRegisterSuccess: () -> Unit,
        onRegisterFailure: (String) -> Unit
    ) {
        val fireStore = Firebase.firestore
        val db = fireStore.collection("stuff")
        val key = db.document().id

        if (email.isBlank() || password.isBlank() || name.isBlank() || surname.isBlank()) {
            onRegisterFailure("Заполните все поля.")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    db.document(key)
                        .set(
                            employee.copy(
                                key = key,
                                name = name,
                                surname = surname,
                                jobTitle = jobTitle
                            )
                        )
                        .addOnFailureListener { e ->
                            onRegisterFailure(e.message ?: "Ошибка при добавлении сотрудника")
                        }
                        .addOnSuccessListener {
                            onRegisterSuccess()
                        }
                }
            }
    }

    fun editAnEmployee(
        key: String,
        name: String,
        surname: String,
        jobTitle: String,
        onEditSuccess: () -> Unit,
        onEditFailure: (String) -> Unit
    ) {
        val fireStore = Firebase.firestore
        val db = fireStore.collection("stuff")

        if (name.isBlank() || surname.isBlank() || (jobTitle.isBlank())) {
            onEditFailure("Заполните все поля.")
            return
        }

        db.document(key)
            .set(
                Employee(
                    key = key,
                    name = name,
                    surname = surname,
                    jobTitle = jobTitle
                ),
                SetOptions.merge()
            )
            .addOnFailureListener { e ->
                onEditFailure(e.message ?: "Ошибка при обновлении сотрудника")
            }
            .addOnSuccessListener {
                onEditSuccess()
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

    fun deleteAccount(
        key: String,
        email: String,
        password: String,
        onDeleteSuccess: () -> Unit,
        onDeleteFailure: (String) -> Unit
    ) {
        val fireStore = Firebase.firestore
        val db = fireStore.collection("stuff")

        db.document(key).delete()
            .addOnSuccessListener {
                onDeleteSuccess()
            }
            .addOnFailureListener { e ->
                onDeleteFailure(e.message ?: "Ошибка при удалении записи")
            }
    }
}