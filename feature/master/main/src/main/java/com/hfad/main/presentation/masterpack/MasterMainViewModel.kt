package com.hfad.main.presentation.masterpack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.hfad.data.repository.LoginRepository
import com.hfad.ui.profile.uimodel.EmployeeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MasterMainViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(MasterMainScreenState(emptyList()))
    private set

    data class MasterMainScreenState(
        val employeeList: List<EmployeeUiModel>
    )
}