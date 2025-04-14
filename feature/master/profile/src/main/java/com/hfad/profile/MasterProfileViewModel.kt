package com.hfad.profile

import androidx.lifecycle.ViewModel
import com.hfad.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MasterProfileViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    fun onSignOutClick() {
        repository.signOut()
    }
}