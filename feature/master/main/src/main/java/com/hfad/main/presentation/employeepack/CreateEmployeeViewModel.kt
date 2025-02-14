package com.hfad.main.presentation.employeepack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.data.repository.LoginRepository
import com.hfad.navigation.Screen
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

    fun onEmailInputChanged(newInput: String) {
        state = state.copy(emailInput = newInput)
    }

    fun onPasswordInputChanged(newInput: String) {
        state = state.copy(passwordInput = newInput)
    }

    fun onNameInputChanged(newInput: String) {
        state = state.copy(nameInput = newInput)
    }

    fun onSurnameInputChanged(newInput: String) {
        state = state.copy(surnameInput = newInput)
    }

    fun onJobTitleInputChanged(newInput: String) {
        state = state.copy(jobTitleInput = newInput)
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
        val jobTitleInput: String = ""
    )
}