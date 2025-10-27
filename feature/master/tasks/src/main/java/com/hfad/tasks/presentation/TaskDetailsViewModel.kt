package com.hfad.tasks.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.hfad.data.repository.LoginRepository
import com.hfad.model.WorkTask
import com.hfad.navigation.Screen
import com.hfad.ui.profile.uimodel.TaskUiModel
import com.hfad.ui.profile.uimodel.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val args = stateHandle.toRoute<Screen.TaskDetailsScreen>()
    private val navigationChannel = Channel<NavigationEvent>()
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()

    var state by mutableStateOf(
        TaskDetailsScreenState(
            task = TaskUiModel(
                key = args.key,
                title = args.title,
                quantity = args.quantity,
                doneCount = 0,
                done = false
            ),
            tasksList = emptyList()
        )
    )
        private set

    init {
        observeTask()
    }

    private fun observeTask() {
        viewModelScope.launch {
            activeTaskFlow()
                .collect { task ->
                    state = state.copy(
                        task = task
                    )
                }
        }
    }

    private fun activeTaskFlow():
            Flow<TaskUiModel> = callbackFlow {
        val db = Firebase.firestore
        val listenerRegistration = db.collection("tasks")
            .document(state.task.key)
            .addSnapshotListener { snapshot, error ->
                error?.let {
                    close(it)
                    return@addSnapshotListener
                }

                snapshot?.let { document ->
                    if (document.exists()) {
                        val workTask = document.toObject(WorkTask::class.java)
                        workTask?.let {
                            trySend(it.toUiModel())
                        }
                    }
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }

    fun closeTask() {
        loginRepository.closeTask(
            taskKey = state.task.key,
            onCloseSuccess = {
                viewModelScope.launch {
                    navigationChannel.send(NavigationEvent.OnClosed)
                }
            },
            onCloseFailure = { error ->
                Log.e("Firebase", "Error closing task: $error")
            }
        )
    }

    sealed interface NavigationEvent {
        data object OnClosed : NavigationEvent
    }

    data class TaskDetailsScreenState(
        val task: TaskUiModel,
        val tasksList: List<TaskUiModel>
    )
}