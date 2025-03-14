package com.hfad.tasks.presentation

import Icons.FzIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hfad.designsystem.components.theme.LightColorScheme
import com.hfad.main.R
import com.hfad.navigation.Screen
import com.hfad.ui.profile.SelectedEmployeeCard

@Composable
fun CreateNewTaskScreen() {
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.NewTaskText),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            color = textColor
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.AddPhoto),
            fontSize = 18.sp,
            fontFamily = FontFamily.Default,
            color = Color.LightGray
        )
        TitleTaskInputField()
        TaskQuantityInputField()
        Text(
            text = stringResource(R.string.SelectAnEmployee),
            fontSize = 18.sp,
            fontFamily = FontFamily.Default,
            color = Color.LightGray
        )
        SelectedEmployeeCard()
        SelectedEmployeeCard()
        SelectedEmployeeCard()
    }
}

