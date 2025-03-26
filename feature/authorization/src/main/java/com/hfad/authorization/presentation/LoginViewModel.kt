package com.hfad.authorization.presentation

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
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val navigationChannel = Channel<NavigationEvent>()
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()

    var loginState by mutableStateOf(LoginScreenState())
        private set

    fun onCreateAccountClick() {
        if (loginState.emailInput.isBlank() || loginState.passwordInput.isBlank()) {
            loginState = loginState.copy(errorState = "Для регистрации мастера, введите логин и пароль.")
            return
        }
        repository.createAnAccount(
            email = loginState.emailInput,
            password = loginState.passwordInput,
            onSignUpSuccess = {
                viewModelScope.launch {
                    navigationChannel.send(NavigationEvent.OnRegistered)
                }
                loginState = loginState.copy(errorState = "")
            },
            onSignUpFailure = { error ->
                loginState = loginState.copy(errorState = error)
            }
        )
    }

    fun onSignInClick() {
        if (loginState.emailInput.isBlank() || loginState.passwordInput.isBlank()) {
            loginState = loginState.copy(errorState = "Логин и пароль не могут быть пустыми.")
            return
        }
        repository.signIn(
            email = loginState.emailInput,
            password = loginState.passwordInput,
            onSignInSuccess = {
                viewModelScope.launch {
                    navigationChannel.send(NavigationEvent.OnSignedIn)
                }
                loginState = loginState.copy(errorState = "")
            },
            onSignInFailure = { error ->
                loginState = loginState.copy(errorState = error)
            }
        )
    }

    fun onSignOutClick() {
        repository.signOut()
    }
    
    fun onCreateEmployeeAccountClick() {
        viewModelScope.launch {
            navigationChannel.send(NavigationEvent.OnRegisterEmployeeClicked)
        }
    }

    fun onEmailInputChanged(newInput: String) {
        loginState = loginState.copy(emailInput = newInput)
    }

    fun onPasswordInputChanged(newInput: String) {
        loginState = loginState.copy(passwordInput = newInput)
    }
}

sealed interface NavigationEvent {
    data object OnSignedIn : NavigationEvent

    data object OnRegistered : NavigationEvent

    data object OnRegisterEmployeeClicked : NavigationEvent
}

data class LoginScreenState(
    val emailInput: String = "",
    val passwordInput: String = "",
    val errorState: String = ""
)
