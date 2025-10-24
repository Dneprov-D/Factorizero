package com.hfad.tasks.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.hfad.model.WorkTask
import com.hfad.ui.profile.uimodel.TaskUiModel
import com.hfad.ui.profile.uimodel.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoneTasksScreenViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(DoneTasksScreenState(emptyList()))
        private set

    init {
        observeDoneTasks()
    }

    data class DoneTasksScreenState(
        val tasksList: List<TaskUiModel>
    )

    private fun observeDoneTasks() {
        viewModelScope.launch {
            doneTasksFlow()
                .onStart {
                    state = state.copy()
                }
                .catch { exception ->
                    state = state.copy()
                }
                .collect { tasks ->
                    state = state.copy(
                        tasksList = tasks
                    )
                }
        }
    }

    private fun doneTasksFlow(): Flow<List<TaskUiModel>> = callbackFlow {
        val db = Firebase.firestore
        val listenerRegistration = db.collection("tasks")
            .whereEqualTo("isDone", true)
            .addSnapshotListener { snapshot, error ->
                error?.let {
                    close(it)
                    return@addSnapshotListener
                }

                snapshot?.let { querySnapshot ->
                    val tasksList = querySnapshot.toObjects(WorkTask::class.java)
                        .map { it.toUiModel() }
                    trySend(tasksList)
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }
}