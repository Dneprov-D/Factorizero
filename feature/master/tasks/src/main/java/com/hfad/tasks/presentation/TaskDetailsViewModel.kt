package com.hfad.tasks.presentation

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
class TaskDetailsViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    private val args = stateHandle.toRoute<Screen.TaskDetailsScreen>()

    var state by mutableStateOf(
        TaskDetailsScreenState(
            task = TaskUiModel(
                key = args.key,
                title = args.title,
                quantity = args.quantity,
                doneCount = args.doneCount
            )
        )
    )
        private set

    data class TaskDetailsScreenState(
        val task: TaskUiModel
    )
}