package com.hfad.main.presentation.employeepack


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import buttons.FzOutlinedButton
import com.hfad.ui.R
import com.hfad.ui.profile.TaskCard
import com.hfad.ui.profile.TaskCardWrapper

@Composable
fun EmployeeDetails(navController: NavHostController) {
    val textColor = MaterialTheme.colorScheme.onBackground
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = R.drawable.employeeorc),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column {
            Text(
                text = "Имя Фамилия",
                fontSize = 25.sp,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Должность",
                fontSize = 20.sp
            )
        }
    }
    Column(
        modifier = Modifier
            .padding(15.dp)
            .padding(top = 135.dp)
    ) {
        Text(
            text = stringResource(com.hfad.main.R.string.TasksInWorkText),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            color = textColor
        )
        Spacer(modifier = Modifier.height(15.dp))
        TaskCard(navController)
        Spacer(modifier = Modifier.height(15.dp))
        TaskCard(navController)
        Spacer(modifier = Modifier.height(15.dp))
        TaskCard(navController)
        FzOutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { /*TODO*/ },
            text = { Text(stringResource(com.hfad.main.R.string.EditTasksText)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.EditNote,
                    contentDescription = null
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsOFEmployeeWrapper() {
    val textColor = MaterialTheme.colorScheme.onBackground
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = R.drawable.employeeorc),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column {
            Text(
                text = "Имя Фамилия",
                fontSize = 25.sp,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Должность",
                fontSize = 20.sp
            )
        }
    }
    Column(
        modifier = Modifier
            .padding(15.dp)
            .padding(top = 135.dp)
    ) {
        Text(
            text = stringResource(com.hfad.main.R.string.TasksInWorkText),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            color = textColor
        )
        Spacer(modifier = Modifier.height(15.dp))
        TaskCardWrapper()
        Spacer(modifier = Modifier.height(15.dp))
        TaskCardWrapper()
        Spacer(modifier = Modifier.height(15.dp))
        TaskCardWrapper()
        FzOutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { /*TODO*/ },
            text = { Text(stringResource(com.hfad.main.R.string.EditTasksText)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.EditNote,
                    contentDescription = null
                )
            }
        )
    }
}