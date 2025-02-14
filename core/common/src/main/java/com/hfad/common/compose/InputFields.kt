package com.hfad.common.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.FormatItalic
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation


@Composable
fun InputFieldLogin(emailInput: String, onEmailInputChanged: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.Email,
                        contentDescription = null
                    )
                }
            },
            value = emailInput,
            onValueChange = { onEmailInputChanged(it) },
            label = { Text(stringResource(com.hfad.common.R.string.LoginText)) },
            shape = MaterialTheme.shapes.small,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions()
        )
    }
}

@Composable
fun InputFieldPassword(passwordInput: String, onPasswordInputChanged: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.Password,
                        contentDescription = null
                    )
                }
            },
            value = passwordInput,
            onValueChange = { onPasswordInputChanged(it) },
            label = { Text(stringResource(com.hfad.common.R.string.PasswordText)) },
            shape = MaterialTheme.shapes.small,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardActions = KeyboardActions()
        )
    }
}

@Composable
fun InputFieldName(nameInput: String, onNameInputChanged: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.ContentPaste,
                        contentDescription = null
                    )
                }
            },
            value = nameInput,
            onValueChange = { onNameInputChanged(it) },
            label = { Text(stringResource(com.hfad.common.R.string.NameText)) },
            shape = MaterialTheme.shapes.small,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions()
        )
    }
}

@Composable
fun InputFieldSurname(surnameInput: String, onSurnameInputChanged: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.ContentPaste,
                        contentDescription = null
                    )
                }
            },
            value = surnameInput,
            onValueChange = { onSurnameInputChanged(it) },
            label = { Text(stringResource(com.hfad.common.R.string.SurnameText)) },
            shape = MaterialTheme.shapes.small,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions()
        )
    }
}

@Composable
fun InputFieldJobTitle(jobTitleInput: String, onJobTitleInputChanged: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.FormatItalic,
                        contentDescription = null
                    )
                }
            },
            value = jobTitleInput,
            onValueChange = { onJobTitleInputChanged(it) },
            label = { Text(stringResource(com.hfad.common.R.string.JobTitle)) },
            shape = MaterialTheme.shapes.small,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions()
        )
    }
}