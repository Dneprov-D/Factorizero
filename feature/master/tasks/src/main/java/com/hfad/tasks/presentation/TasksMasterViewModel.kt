package com.hfad.tasks.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hfad.model.WorkTask
import com.hfad.ui.profile.uimodel.TaskUiModel
import com.hfad.ui.profile.uimodel.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksMasterViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(TasksMasterScreenState(emptyList()))
        private set

    init {
        val db = Firebase.firestore
        getTasks(db)
    }

    data class TasksMasterScreenState(
        val tasksList: List<TaskUiModel>
    )

    private fun getTasks(
        db: FirebaseFirestore,
    ) {
        db.collection("tasks")
            .get()
            .addOnSuccessListener { task ->
                val tasksList = task.toObjects(WorkTask::class.java)
                state = state.copy(
                    tasksList = tasksList.map {
                        it.toUiModel()
                    }
                )
            }
    }
}