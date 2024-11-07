package com.hfad.authorization.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import buttons.FzButton
import com.hfad.authorization.R
import com.hfad.authorization.presentation.InputFieldLogin
import com.hfad.authorization.presentation.InputFieldPassword


@Preview(showBackground = true)
@Composable
fun MainLoginScreen() {
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.factorizerText),
            fontSize = 32.sp,
            fontFamily = FontFamily.Serif,
            color = textColor
        )
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            modifier = Modifier
                .size(230.dp),
            painter = painterResource(id = R.drawable.torbj),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.EnterToProfileText),
            fontSize = 17.sp,
            color = textColor
        )
        InputFieldLogin()
        InputFieldPassword()
        Spacer(modifier = Modifier.height(10.dp))
        FzButton(
            onClick = { /*TODO*/ },
            text = { Text(text = stringResource(R.string.LogIn))}
        )
    }
}
