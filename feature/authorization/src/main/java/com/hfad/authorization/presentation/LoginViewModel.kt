package com.hfad.authorization.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
        if (loginState.emailInput.isBlank() || loginState.passwordInput.isBlank()) {
            loginState = loginState.copy(errorState = "Логин и пароль не могут быть пустыми")
            return
        }

        repository.createAnAccount(
            email = loginState.emailInput,
            password = loginState.passwordInput,
            onSignUpSuccess = {
                // Здесь обработка успешного создания аккаунта.
                loginState = loginState.copy(errorState = "") // Очистка ошибки, если нужно.
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
            onSignInSuccess = {
                // Здесь обработка успешного создания аккаунта.
                loginState = loginState.copy(errorState = "") // Очистка ошибки, если нужно.
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

data class LoginScreenState(
    val emailInput: String = "",
    val passwordInput: String = "",
    val errorState: String = ""
)