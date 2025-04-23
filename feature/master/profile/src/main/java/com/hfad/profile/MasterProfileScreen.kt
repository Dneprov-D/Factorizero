package com.hfad.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import buttons.FzRedOutlinedButton

@Composable
fun MasterProfileScreen(
    viewModel: MasterProfileViewModel = hiltViewModel()
) {

    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    val textColor = MaterialTheme.colorScheme.onBackground

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(7.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.textProfile),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = textColor
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            FzRedOutlinedButton(
                onClick = { showDeleteDialog = true },
                text = { Text(text = stringResource(R.string.ExitProfile)) }
            )
            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text(text = stringResource(R.string.ConfirmSignOut)) },
                    text = { Text(text = stringResource(R.string.SureSignOut)) },
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.onSignOutClick()
                                showDeleteDialog = false
                            }) {
                            Text(
                                text = stringResource(R.string.Yes)
                            )
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showDeleteDialog = false }) {
                            Text(
                                text = stringResource(R.string.No)
                            )
                        }
                    }
                )
            }
        }
    }
}