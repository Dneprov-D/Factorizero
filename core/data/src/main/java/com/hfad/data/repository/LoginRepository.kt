package com.hfad.data.repository

import android.util.Log
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.hfad.common.compose.navigateToNewRoot
import com.hfad.model.Employee
import com.hfad.model.Master
import com.hfad.model.WorkTask
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

    fun createMasterAccount(
        master: Master,
        email: String,
        password: String,
        name: String,
        surname: String,
        jobTitle: String,
        onRegisterSuccess: () -> Unit,
        onRegisterFailure: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            onRegisterFailure("Login and Password cannot be empty")
            return
        }
        val fireStore = Firebase.firestore
        val db = fireStore.collection("master")
        val key = db.document().id

        if (email.isBlank() || password.isBlank() || name.isBlank() || surname.isBlank()) {
            onRegisterFailure("Заполните все поля.")
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = task.result.user?.uid ?: ""
                    Log.d("FirebaseUID", "Создан мастер с uid: $uid")
                    db.document(key)
                        .set(
                            master.copy(
                                key = key,
                                name = name,
                                surname = surname,
                                jobTitle = jobTitle,
                                uid = uid
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
        val db = fireStore.collection("staff")
        val key = db.document().id

        if (email.isBlank() || password.isBlank() || name.isBlank() || surname.isBlank()) {
            onRegisterFailure("Заполните все поля.")
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = task.result.user?.uid ?: ""
                    Log.d("FirebaseUID", "Создан сотрудник с uid: $uid")
                    db.document(key)
                        .set(
                            employee.copy(
                                key = key,
                                name = name,
                                surname = surname,
                                jobTitle = jobTitle,
                                uid = uid
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
        val db = fireStore.collection("staff")

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

    fun editTask(
        key: String,
        title: String,
        quantity: String,
        onEditSuccess: () -> Unit,
        onEditFailure: (String) -> Unit
    ) {
        val fireStore = Firebase.firestore
        val db = fireStore.collection("tasks")
//        val key = db.document().id

        if (title.isBlank() || quantity.isBlank()) {
            onEditFailure("Заполните все поля.")
            return
        }

        db.document(key)
            .set(
                WorkTask(
                    key = key,
                    title = title,
                    quantity = quantity
                ),
                SetOptions.merge()
            )
            .addOnFailureListener { e ->
                onEditFailure(e.message ?: "Ошибка при обновлении задачи.")
            }
            .addOnSuccessListener {
                onEditSuccess()
            }
    }

    fun closeTask(
        taskKey: String,
        onCloseSuccess: () -> Unit,
        onCloseFailure: (String) -> Unit
    ) {
        val fireStore = Firebase.firestore
        val db = fireStore.collection("tasks")

        if (taskKey.isBlank()) {
            onCloseFailure("Ключ задачи не может быть пустым")
            return
        }

        db.document(taskKey)
            .update("isDone", true)
            .addOnSuccessListener {
                onCloseSuccess()
            }
            .addOnFailureListener { e ->
                onCloseFailure(e.message ?: "Ошибка при закрытии задачи")
            }
    }

    fun createNewTask(
        title: String,
        quantity: String,
        onCreateSuccess: () -> Unit,
        onCreateFailure: (String) -> Unit
    ) {
        val fireStore = Firebase.firestore
        val db = fireStore.collection("tasks")
        val key = db.document().id

        if (title.isBlank() || quantity.isBlank()) {
            onCreateFailure("Заполните все поля.")
            return
        }
        db.document(key)
            .set(
                WorkTask(
                    key = key,
                    title = title,
                    quantity = quantity,
                    doneCount = 0,
                    isDone = false
                )
            )
            .addOnFailureListener { e ->
                onCreateFailure(e.message ?: "Ошибка при добавлении задачи")
            }
            .addOnSuccessListener {
                onCreateSuccess()
            }
    }

    fun updateDoneCount(
        key: String,
        doneCount: Int,
        onUpdateSuccess: () -> Unit,
        onUpdateFailure: (String) -> Unit
    ) {
        val fireStore = Firebase.firestore
        val db = fireStore.collection("tasks")

        db.document(key)
            .update("doneCount", doneCount)
            .addOnSuccessListener {
                onUpdateSuccess()
            }
            .addOnFailureListener { e ->
                onUpdateFailure(e.message ?: "Ошибка при обновлении количества")
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

    fun loadTask(
        key: String,
        onLoadSuccess: (WorkTask) -> Unit,
        onLoadFailure: (String) -> Unit
    ) {
        val fireStore = Firebase.firestore
        val db = fireStore.collection("tasks")

        db.document(key)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val key = document.getString("key") ?: ""
                    val title = document.getString("title") ?: ""
                    val quantity = document.getString("quantity") ?: ""
                    val doneCount = document.getLong("doneCount")?.toInt() ?: 0

                    if (key.isBlank() || title.isBlank() || quantity.isBlank()) {
                        onLoadFailure("Неполные данные задачи")
                        return@addOnSuccessListener
                    }

                    val task = WorkTask(
                        key = key,
                        title = title,
                        quantity = quantity,
                        doneCount = doneCount
                    )
                    onLoadSuccess(task)
                } else {
                    onLoadFailure("Задача не найдена")
                }
            }
            .addOnFailureListener { exception ->
                onLoadFailure("Ошибка при загрузке задачи: ${exception.message}")
            }
    }

    fun deleteTask(
        key: String,
        onDeleteSuccess: () -> Unit,
        onDeleteFailure: (String) -> Unit
    ) {
        val fireStore = Firebase.firestore
        val db = fireStore.collection("tasks")

        db.document(key).delete()
            .addOnSuccessListener {
                onDeleteSuccess()
            }
            .addOnFailureListener { e ->
                onDeleteFailure(e.message ?: "Ошибка при удалении записи")
            }
    }

    fun deleteAccount(
        key: String,
        onDeleteSuccess: () -> Unit,
        onDeleteFailure: (String) -> Unit
    ) {
        val fireStore = Firebase.firestore
        val db = fireStore.collection("staff")

        db.document(key).delete()
            .addOnSuccessListener {
                onDeleteSuccess()
            }
            .addOnFailureListener { e ->
                onDeleteFailure(e.message ?: "Ошибка при удалении записи")
            }
    }
}