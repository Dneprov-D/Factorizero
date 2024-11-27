package com.hfad.main.presentation

import Icons.FzIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.rounded.Key
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hfad.designsystem.components.theme.LightColorScheme
import com.hfad.main.R
import com.hfad.navigation.BottomBarMaster
import com.hfad.navigation.Screen
import com.hfad.ui.profile.EmployeeCard
import com.hfad.ui.profile.EmployeeCardWrapper

@Composable
fun MasterMainScreen(navController: NavHostController) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground
    Scaffold(
        modifier = Modifier
            .background(color = backgroundColor),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(route = Screen.CreateNewEmployee)
                },
                containerColor = LightColorScheme.tertiary
            ) {
                Icon(FzIcons.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
                .padding(15.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.EmployeeText),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = textColor
            )
            Spacer(modifier = Modifier.height(15.dp))
            EmployeeCard(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MasterMainScreenWrapper() {
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground
    Scaffold(
        modifier = Modifier
            .background(color = backgroundColor),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = LightColorScheme.tertiary
            ) {
                Icon(FzIcons.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
                .padding(15.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.EmployeeText),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = textColor
            )
            Spacer(modifier = Modifier.height(15.dp))
            EmployeeCardWrapper()
        }
    }
}
