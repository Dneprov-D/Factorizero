package com.hfad.authorization.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hfad.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    var loginState by mutableStateOf(LoginScreenState())
        private set
    fun onCreateAccountClick() {
        repository.createAnAccount(
            loginState.emailInput,
            loginState.passwordInput
        )
    }

    fun onSignUpClick() {
        repository.signIn(
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

data class LoginScreenState(
    val emailInput: String = "",
    val passwordInput: String = ""
)