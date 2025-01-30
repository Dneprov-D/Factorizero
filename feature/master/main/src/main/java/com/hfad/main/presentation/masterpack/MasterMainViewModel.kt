package com.hfad.main.presentation.masterpack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hfad.model.Employee
import com.hfad.ui.profile.uimodel.EmployeeUiModel
import com.hfad.ui.profile.uimodel.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MasterMainViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(MasterMainScreenState(emptyList()))
        private set


    init {
        val db = Firebase.firestore
        getAllStuff(db)
    }

    data class MasterMainScreenState(
        val employeeList: List<EmployeeUiModel>
    )


    private fun getAllStuff(
        db: FirebaseFirestore,
    ) {
        db.collection("stuff")
            .get()
            .addOnSuccessListener { task ->
                val stuffList = task.toObjects(Employee::class.java)
                state = state.copy(
                    employeeList = stuffList.map {
                        it.toUiModel()
                    }
                )
            }
    }
}