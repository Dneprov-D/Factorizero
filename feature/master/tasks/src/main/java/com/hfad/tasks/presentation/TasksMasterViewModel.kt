package com.hfad.tasks.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
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
class TasksMasterScreenViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(TasksMasterScreenState(emptyList(), isLoading = true))
        private set

    init {
        observeActiveTasks()
    }

    data class TasksMasterScreenState(
        val tasksList: List<TaskUiModel>,
        val isLoading: Boolean = false
    )

    private fun observeActiveTasks() {
        viewModelScope.launch {
            activeTasksFlow()
                .onStart {
                    state = state.copy(isLoading = true)
                }
                .catch { exception ->
                    state = state.copy(isLoading = false)
                }
                .collect { tasks ->
                    state = state.copy(
                        tasksList = tasks,
                        isLoading = false
                    )
                }
        }
    }

    private fun activeTasksFlow(): Flow<List<TaskUiModel>> = callbackFlow {
        val db = Firebase.firestore
        val listenerRegistration = db.collection("tasks")
            .addSnapshotListener { snapshot, error ->
                error?.let {
                    close(it)
                    return@addSnapshotListener
                }

                snapshot?.let { querySnapshot ->
                    val tasksList = querySnapshot.toObjects(WorkTask::class.java)
                    val activeTasks = tasksList
                        .filter { !it.done }
                        .map { it.toUiModel() }
                    trySend(activeTasks)
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }
}