package com.hfad.authorization.presentation.master

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginAsMasterViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    private val navigationChannel = Channel<NavigationEvent>()
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()

    var loginState by mutableStateOf(MasterLoginScreenState())
        private set

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

    fun onCreateMasterAccountClick() {
        viewModelScope.launch {
            navigationChannel.send(NavigationEvent.OnRegisterMasterClicked)
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
    data object OnRegisterMasterClicked : NavigationEvent
}

data class MasterLoginScreenState(
    val emailInput: String = "",
    val passwordInput: String = "",
    val errorState: String = ""
)