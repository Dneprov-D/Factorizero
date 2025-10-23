package com.hfad.main.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.hfad.data.repository.LoginRepository
import com.hfad.navigation.Screen
import com.hfad.ui.profile.uimodel.TaskUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeeTaskDetailsScreenViewModel @Inject constructor(
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
                doneCount = 0
            ),
            editorText = "0",
            isFullScreenImageVisible = false,
            selectedMultiplier = 1,
            isLoading = true,
            isTaskCompleted = false
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
                    task = state.task.copy(doneCount = workTask.doneCount),
                    editorText = workTask.doneCount.toString(),
                    isLoading = false,
                    isTaskCompleted = workTask.doneCount.toString() == state.task.quantity
                )
            },
            onLoadFailure = { error ->
                Log.e("Firebase", "Error loading task: $error")
                state = state.copy(
                    task = state.task.copy(doneCount = args.doneCount),
                    editorText = args.doneCount.toString(),
                    isLoading = false,
                    isTaskCompleted = args.doneCount.toString() == state.task.quantity
                )
            }
        )
    }

    private fun updateDoneCount(newCount: Int) {
        if (newCount != state.task.doneCount) {
            state = state.copy(
                task = state.task.copy(doneCount = newCount),
                editorText = newCount.toString(),
                isTaskCompleted = newCount.toString() == state.task.quantity
            )
            saveToFirebase(newCount)
        }
    }

    private fun saveToFirebase(newCount: Int) {
        loginRepository.updateDoneCount(
            key = state.task.key,
            doneCount = newCount,
            onUpdateSuccess = {
                Log.d("Firebase", "Successfully updated doneCount to $newCount")
            },
            onUpdateFailure = { error ->
                Log.e("Firebase", "Error updating done count: $error")
            }
        )
    }

    fun updateEditorText(newText: String) {
        val filteredText = newText.filter { it.isDigit() }.take(5)
        state = state.copy(
            editorText = filteredText,
            isTaskCompleted = filteredText == state.task.quantity
        )

        val v = filteredText.toIntOrNull()
        val totalQuantity = state.task.quantity.toIntOrNull()

        if (v != null && v != state.task.doneCount) {
            if (totalQuantity == null || v <= totalQuantity) {
                updateDoneCount(v)
            }
        }
    }

    fun validateAndSaveEditorText(totalQuantity: Int?) {
        val v = state.editorText.toIntOrNull()
        if (v != null) {
            if (totalQuantity == null || v <= totalQuantity) {
                updateDoneCount(v)
            }
        } else if (state.task.doneCount != 0) {
            updateDoneCount(0)
        }
    }

    fun setMultiplier(multiplier: Int) {
        state = state.copy(selectedMultiplier = multiplier)
    }

    fun incrementCount(totalQuantity: Int?) {
        val incrementValue = state.selectedMultiplier
        val newValue = if (totalQuantity != null) {
            (state.task.doneCount + incrementValue).coerceAtMost(totalQuantity)
        } else {
            state.task.doneCount + incrementValue
        }
        updateDoneCount(newValue)
    }

    fun decrementCount() {
        val decrementValue = state.selectedMultiplier
        val newValue = (state.task.doneCount - decrementValue).coerceAtLeast(0)
        updateDoneCount(newValue)
    }

    fun setFullScreenImageVisible(visible: Boolean) {
        state = state.copy(isFullScreenImageVisible = visible)
    }

    data class TaskDetailsScreenState(
        val task: TaskUiModel,
        val editorText: String,
        val isFullScreenImageVisible: Boolean,
        val selectedMultiplier: Int,
        val isLoading: Boolean = false,
        val isTaskCompleted: Boolean = false
    )
}