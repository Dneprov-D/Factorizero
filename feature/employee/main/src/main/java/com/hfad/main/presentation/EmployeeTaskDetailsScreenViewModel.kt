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
            isFullScreenImageVisible = false
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
            // Если значение ошибочное, остаёмся в фокусе (не очищаем состояние)
        } else {
            updateDoneCount(0)
        }
    }

    fun incrementCount(totalQuantity: Int?) {
        val newValue = if (totalQuantity != null) {
            (state.doneCount + 1).coerceAtMost(totalQuantity)
        } else {
            state.doneCount + 1
        }
        if (newValue != state.doneCount) {
            updateDoneCount(newValue)
        }
    }

    fun decrementCount() {
        val newValue = (state.doneCount - 1).coerceAtLeast(0)
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
        val isFullScreenImageVisible: Boolean
    )
}