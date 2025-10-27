package com.hfad.tasks.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hfad.data.repository.LoginRepository
import com.hfad.navigation.Screen
import com.hfad.ui.profile.uimodel.TaskUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val repository: LoginRepository,
    stateHandle: SavedStateHandle
) : ViewModel() {

    private val args = stateHandle.toRoute<Screen.EditTaskScreen>()
    private val navigationChannel = Channel<NavigationEvent>()
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()

    var state by mutableStateOf(
        EditTaskState(
            task = TaskUiModel(
                key = args.key,
                title = args.title,
                quantity = args.quantity,
                doneCount = args.doneCount,
                done = args.done
            ),
            titleInput = args.title,
            quantityInput = args.quantity
        )
    )
        private set

    fun onEditTaskClick() {
        repository.editTask(
            key = state.task.key,
            title = state.titleInput,
            quantity = state.quantityInput,
            onEditSuccess = {
                viewModelScope.launch {
                    navigationChannel.send(NavigationEvent.OnEdited)
                }
                state = state.copy(errorState = "")
            },
            onEditFailure = { error ->
                state = state.copy(errorState = error)
            }
        )
    }

    fun onDeleteTaskClick() {
        repository.deleteTask(
            key = state.task.key,
            onDeleteSuccess = {
                viewModelScope.launch {
                    navigationChannel.send(NavigationEvent.OnDeleted)
                }
                state = state.copy(errorState = "")
            },
            onDeleteFailure = { error ->
                state = state.copy(errorState = error)
            }
        )
    }

    private fun shouldButtonEnabled() =
        state.titleInput.isNotBlank()
                && state.quantityInput.isNotBlank()

    fun onTitleInputChanged(newInput: String) {
        state = state.copy(
            titleInput = newInput,
            isButtonEnabled = shouldButtonEnabled()
        )
    }

    fun onQuantityInputChanged(newInput: String) {
        state = state.copy(
            quantityInput = newInput,
            isButtonEnabled = shouldButtonEnabled()
        )
    }

    sealed interface NavigationEvent {
        data object OnEdited : NavigationEvent
        data object OnDeleted : NavigationEvent
    }

    data class EditTaskState(
        val task: TaskUiModel,
        val key: String = "",
        val title: String = "",
        val quantity: String = "",
        val titleInput: String = "",
        val quantityInput: String = "",
        val errorState: String = "",
        val isButtonEnabled: Boolean = false,
        val done: Boolean = false
    )
}