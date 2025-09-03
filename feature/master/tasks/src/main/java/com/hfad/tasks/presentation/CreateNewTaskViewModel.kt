package com.hfad.tasks.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.hfad.data.repository.LoginRepository
import com.hfad.model.Employee
import com.hfad.ui.profile.uimodel.EmployeeUiModel
import com.hfad.ui.profile.uimodel.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewTaskViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val navigationChannel = Channel<NavigationEvent>()
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()
    var state by mutableStateOf(CreateNewTaskState(emptyList()))
        private set

    init {
        val db = Firebase.firestore
        getAllStaff(db)
        Log.e("pop", "суперметка! $this")
    }

    fun onTaskCreateClick() {
        if (state.titleInput.isBlank() || state.quantityInput.isBlank()) {
            state = state.copy(errorState = "Заполните все поля.")
            return
        }
        repository.createNewTask(
            title = state.titleInput,
            quantity = state.quantityInput,
            onCreateSuccess = {
                viewModelScope.launch {
                    navigationChannel.send(NavigationEvent.OnTaskCreated)
                }
                state = state.copy(errorState = "")
            },
            onCreateFailure = { error ->
                state = state.copy(errorState = error)
            })
    }

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

    fun onTitleInputChanged(newInput: String) {
        state = state.copy(
            titleInput = newInput
        )
    }

    fun onQuantityInputChanged(newInput: String) {
        state = state.copy(
            quantityInput = newInput
        )
    }

    sealed interface NavigationEvent {
        data object OnTaskCreated : NavigationEvent
    }

    data class CreateNewTaskState(
        val employeeList: List<EmployeeUiModel>,
        val errorState: String = "",
        var titleInput: String = "",
        val quantityInput: String = "",
    )
}