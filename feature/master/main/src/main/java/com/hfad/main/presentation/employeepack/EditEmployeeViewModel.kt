package com.hfad.main.presentation.employeepack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hfad.data.repository.LoginRepository
import com.hfad.navigation.Screen
import com.hfad.ui.profile.uimodel.EmployeeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEmployeeViewModel @Inject constructor(
    private val repository: LoginRepository,
    stateHandle: SavedStateHandle
) : ViewModel() {
    private val args = stateHandle.toRoute<Screen.EditEmployeeScreen>()
    private val navigationChannel = Channel<NavigationEvent>()
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()

    var state by mutableStateOf(
        EditScreenState(
            employee = EmployeeUiModel(
                key = args.key,
                name = args.name,
                surname = args.surname,
                jobTitle = args.jobTitle,
            ),
            nameInput = args.name,
            surnameInput = args.surname,
            jobTitleInput = args.jobTitle
        )
    )
        private set

    fun onEditEmployeeClick() {
        repository.editAnEmployee(
            key = state.employee.key,
            name = state.nameInput,
            surname = state.surnameInput,
            jobTitle = state.jobTitleInput,
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

    fun onDeleteAccountClick() {
        repository.deleteAccount(
            key = state.employee.key,
            email = state.emailInput,
            password = state.passwordInput,
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
        state.nameInput.isNotBlank()
                && state.surnameInput.isNotBlank()
                && state.jobTitleInput.isNotBlank()


    fun onNameInputChanged(newInput: String) {
        state = state.copy(
            nameInput = newInput,
            isButtonEnabled = shouldButtonEnabled()
        )
    }

    fun onSurnameInputChanged(newInput: String) {
        state = state.copy(
            surnameInput = newInput,
            isButtonEnabled = shouldButtonEnabled()
        )
    }

    fun onJobTitleInputChanged(newInput: String) {
        state = state.copy(
            jobTitleInput = newInput,
            isButtonEnabled = shouldButtonEnabled()
        )
    }

    sealed interface NavigationEvent {
        data object OnEdited : NavigationEvent
        data object OnDeleted : NavigationEvent
    }

    data class EditScreenState(
        val employee: EmployeeUiModel,
        val key: String = "",
        val emailInput: String = "",
        val passwordInput: String = "",
        val errorState: String = "",
        val nameInput: String = "",
        val surnameInput: String = "",
        val jobTitleInput: String = "",
        val isButtonEnabled: Boolean = false
    )
}