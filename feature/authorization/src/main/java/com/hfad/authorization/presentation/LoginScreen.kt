package com.hfad.authorization.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import buttons.FzButton
import com.hfad.authorization.R
import com.hfad.common.compose.ObserveAsEvents

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onMasterClick: () -> Unit,
    onEmployeeClick: () -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.factorizerText),
            fontSize = 32.sp,
            fontFamily = FontFamily.Serif,
            color = textColor
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.MasterOrEmployee),
            fontSize = 17.sp,
            color = textColor
        )

        Spacer(modifier = Modifier.height(10.dp))
        FzButton(
            onClick = onMasterClick,
            text = { Text(text = stringResource(R.string.MasterText)) }
        )

        Spacer(modifier = Modifier.height(5.dp))
        FzButton(
            onClick = onEmployeeClick,
            text = { Text(text = stringResource(R.string.EmployeeText)) }
        )
    }
}
