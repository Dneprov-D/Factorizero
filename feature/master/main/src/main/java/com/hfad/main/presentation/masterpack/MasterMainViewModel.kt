package com.hfad.main.presentation.masterpack

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.hfad.model.Employee
import com.hfad.ui.profile.uimodel.EmployeeUiModel
import com.hfad.ui.profile.uimodel.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@HiltViewModel
class MasterMainViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(MasterMainScreenState(emptyList()))
        private set

    init {
        val db = Firebase.firestore
        getAllStaff(db)
        Log.e("pop", "суперметка! $this")
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("pop", "onCleared $this")
    }

    data class MasterMainScreenState(
        val employeeList: List<EmployeeUiModel>
    )

    private fun getAllStaff(
        db: FirebaseFirestore,
    ) {
        db.collection("stuff")
            .get()
            .addOnSuccessListener { task ->
                val staffList = task.toObjects(Employee::class.java)
                state = state.copy(
                    employeeList = staffList.map {
                        it.toUiModel()
                    }
                )
            }
    }

    fun refreshEmployees() {
        val db = Firebase.firestore
        getAllStaff(db)
    }
}