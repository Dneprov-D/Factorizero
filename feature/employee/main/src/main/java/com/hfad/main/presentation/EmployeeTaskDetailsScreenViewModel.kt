package com.hfad.main.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.hfad.navigation.Screen
import com.hfad.ui.profile.uimodel.TaskUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeeTaskDetailsScreenViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    private val args = stateHandle.toRoute<Screen.TaskDetailsScreen>()

    var state by mutableStateOf(
        TaskDetailsScreenState(
            task = TaskUiModel(
                key = args.key,
                title = args.title,
                quantity = args.quantity
            ),
            doneCount = 0,
            editorText = "0",
            isFullScreenImageVisible = false,
            selectedMultiplier = 1
        )
    )
        private set

    fun updateDoneCount(newCount: Int) {
        state = state.copy(
            doneCount = newCount,
            editorText = newCount.toString()
        )
    }

    fun updateEditorText(newText: String) {
        state = state.copy(editorText = newText.filter { it.isDigit() })
    }

    fun validateAndSaveEditorText(totalQuantity: Int?) {
        val v = state.editorText.toIntOrNull()
        if (v != null) {
            if (totalQuantity == null || v <= totalQuantity) {
                updateDoneCount(v)
            }
        } else {
            updateDoneCount(0)
        }
    }

    fun setMultiplier(multiplier: Int) {
        state = state.copy(selectedMultiplier = multiplier)
    }

    fun incrementCount(totalQuantity: Int?) {
        val incrementValue = state.selectedMultiplier
        val newValue = if (totalQuantity != null) {
            (state.doneCount + incrementValue).coerceAtMost(totalQuantity)
        } else {
            state.doneCount + incrementValue
        }
        if (newValue != state.doneCount) {
            updateDoneCount(newValue)
        }
    }

    fun decrementCount() {
        val decrementValue = state.selectedMultiplier
        val newValue = (state.doneCount - decrementValue).coerceAtLeast(0)
        if (newValue != state.doneCount) {
            updateDoneCount(newValue)
        }
    }

    fun setFullScreenImageVisible(visible: Boolean) {
        state = state.copy(isFullScreenImageVisible = visible)
    }

    data class TaskDetailsScreenState(
        val task: TaskUiModel,
        val doneCount: Int,
        val editorText: String,
        val isFullScreenImageVisible: Boolean,
        val selectedMultiplier: Int
    )
}