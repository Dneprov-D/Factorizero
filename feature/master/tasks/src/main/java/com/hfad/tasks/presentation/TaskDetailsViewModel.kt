package com.hfad.tasks.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.hfad.data.repository.LoginRepository
import com.hfad.navigation.Screen
import com.hfad.ui.profile.uimodel.TaskUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val args = stateHandle.toRoute<Screen.TaskDetailsScreen>()

    var state by mutableStateOf(
        TaskDetailsScreenState(
            task = TaskUiModel(
                key = args.key,
                title = args.title,
                quantity = args.quantity,
                doneCount = 0,
                isDone = false
            )
        )
    )
        private set

    init {
        loadTask()
    }

    fun loadTask() {
        loginRepository.loadTask(
            key = state.task.key,
            onLoadSuccess = { workTask ->
                state = state.copy(
                    task = state.task.copy(
                        title = workTask.title,
                        quantity = workTask.quantity,
                        doneCount = workTask.doneCount,
                        isDone = workTask.isDone
                    )
                )
            },
            onLoadFailure = { error ->
                Log.e("Firebase", "Error loading task: $error")
                state = state.copy(
                    task = state.task.copy(doneCount = args.doneCount)
                )
            }
        )
    }

    fun closeTask() {
        loginRepository.closeTask(
            taskKey = state.task.key,
            onCloseSuccess = {
                // Задача закрыта, можно выполнить навигацию
                // Это будет обработано в Composable через колбэк
            },
            onCloseFailure = { error ->
                Log.e("Firebase", "Error closing task: $error")
            }
        )
    }

    data class TaskDetailsScreenState(
        val task: TaskUiModel
    )
}