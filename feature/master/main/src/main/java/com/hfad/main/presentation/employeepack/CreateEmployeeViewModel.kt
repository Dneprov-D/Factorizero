
package com.hfad.main.presentation.employeepack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.authorization.presentation.NavigationEvent
import com.hfad.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEmployeeViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    var loginState by mutableStateOf(RegisterScreenState())
        private set

    private val navigationChannel = Channel<NavigationEvent>()

    fun onCreateEmployeeClick() {
        if (loginState.emailInput.isBlank() || loginState.passwordInput.isBlank()) {
            loginState = loginState.copy(errorState = "Логин и пароль не могут быть пустыми")
            return
        }
        repository.createAnEmployee(
            email = loginState.emailInput,
            password = loginState.passwordInput,
            name = loginState.nameInput,
            surname = loginState.surnameInput,
            jobTitle = loginState.jobTitleInput,
            onRegisterSuccess = { navData ->
                viewModelScope.launch {
                    navigationChannel.send(NavigationEvent.OnRegistered(navData))
                }
                loginState = loginState.copy(errorState = "")
            },
            onRegisterFailure = { error ->
                loginState = loginState.copy(errorState = error)
            }
        )
    }


    fun onEmailInputChanged(newInput: String) {
        loginState = loginState.copy(emailInput = newInput)
    }

    fun onPasswordInputChanged(newInput: String) {
        loginState = loginState.copy(passwordInput = newInput)
    }

    fun onNameInputChanged(newInput: String) {
        loginState = loginState.copy(nameInput = newInput)
    }

    fun onSurnameInputChanged(newInput: String) {
        loginState = loginState.copy(surnameInput = newInput)
    }

    fun onJobTitleInputChanged(newInput: String) {
        loginState = loginState.copy(jobTitleInput = newInput)
    }

    @Composable
    fun SelectedUri() {

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