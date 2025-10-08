package com.hfad.main.presentation.employeepack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.hfad.navigation.Screen
import com.hfad.ui.profile.uimodel.EmployeeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailsViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle
) : ViewModel() {
    private val args = stateHandle.toRoute<Screen.EmployeeDetailsScreen>()

    var state by mutableStateOf(
        EmployeeDetailsScreenState(
            employee = EmployeeUiModel(
                key = args.key,
                name = args.name,
                surname = args.surname,
                jobTitle = args.jobTitle,
            )
        )
    )
        private set

    data class EmployeeDetailsScreenState(
        val employee: EmployeeUiModel
    )
}
