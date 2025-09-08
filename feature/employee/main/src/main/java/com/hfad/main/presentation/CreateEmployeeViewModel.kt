package com.hfad.main.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.data.repository.LoginRepository
import com.hfad.model.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEmployeeViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    var state by mutableStateOf(RegisterScreenState())
        private set

    private val navigationChannel = Channel<NavigationEvent>()
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()

    fun onCreateEmployeeClick() {
        if (state.emailInput.isBlank() || state.passwordInput.isBlank()) {
            state = state.copy(errorState = "Логин и пароль не могут быть пустыми")
            return
        }
        repository.createAnEmployee(
            employee = Employee(),
            email = state.emailInput,
            password = state.passwordInput,
            name = state.nameInput,
            surname = state.surnameInput,
            jobTitle = state.jobTitleInput,
            onRegisterSuccess = {
                viewModelScope.launch {
                    navigationChannel.send(NavigationEvent.OnRegistered)
                }
                state = state.copy(errorState = "")
            },
            onRegisterFailure = { error ->
                state = state.copy(errorState = error)
            }
        )
    }
    private fun shouldButtonEnabled() =
        state.nameInput.isNotBlank()
                && state.surnameInput.isNotBlank()
                && state.jobTitleInput.isNotBlank()

    fun onEmailInputChanged(newInput: String) {
        state = state.copy(
            emailInput = newInput,
            isButtonEnabled = shouldButtonEnabled()
        )
    }

    fun onPasswordInputChanged(newInput: String) {
        state = state.copy(
            passwordInput = newInput,
            isButtonEnabled = shouldButtonEnabled()
        )
    }

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
        data object OnRegistered : NavigationEvent
    }

    data class RegisterScreenState(
        val emailInput: String = "",
        val passwordInput: String = "",
        val errorState: String = "",
        val nameInput: String = "",
        val surnameInput: String = "",
        val jobTitleInput: String = "",
        val isButtonEnabled: Boolean = false
    )
}