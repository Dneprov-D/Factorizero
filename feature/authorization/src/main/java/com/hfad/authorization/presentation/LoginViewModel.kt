package com.hfad.authorization.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
            loginState = loginState.copy(errorState = "Логин и пароль не могут быть пустыми")
            return
        }
        repository.createAnAccount(
            email = loginState.emailInput,
            password = loginState.passwordInput,
            onSignUpSuccess = { navData ->
                viewModelScope.launch {
                    navigationChannel.send(NavigationEvent.OnRegistered(navData))
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
            loginState = loginState.copy(errorState = "Логин и пароль не могут быть пустыми")
            return
        }
        repository.signIn(
            email = loginState.emailInput,
            password = loginState.passwordInput,
            onSignInSuccess = { navData ->
                viewModelScope.launch {
                    navigationChannel.send(NavigationEvent.OnSignedIn(navData))
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

    fun onDeleteAccountClicked() {
        repository.deleteAccount(
            loginState.emailInput,
            loginState.passwordInput
        )
    }

    fun onEmailInputChanged(newInput: String) {
        loginState = loginState.copy(emailInput = newInput)
    }

    fun onPasswordInputChanged(newInput: String) {
        loginState = loginState.copy(passwordInput = newInput)
    }
}

sealed interface NavigationEvent {
    data class OnSignedIn(val data: Screen.MainScreenDataObject) : NavigationEvent

    data class OnRegistered(val data: Screen.MainScreenDataObject) : NavigationEvent
}

data class LoginScreenState(
    val emailInput: String = "",
    val passwordInput: String = "",
    val errorState: String = ""
)