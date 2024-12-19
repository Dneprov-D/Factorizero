package com.hfad.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hfad.designsystem.components.theme.FactorizeroTheme
import com.hfad.navigation.Screen
import com.hfad.ui.R

@Composable
fun TaskCard(navController: NavHostController) {
    Card(
        modifier = Modifier
            .clickable {
                navController.navigate(route = Screen.TaskDetails)
            }
    )  {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(id = R.drawable.drawing),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(25.dp))
            Column {
                Text(
                    text = "Название детали из заказа (кол-во. шт.)",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCardWrapper() {
    Card(
        modifier = Modifier
            .clickable {
            }
    )  {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(id = R.drawable.drawing),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(25.dp))
            Column {
                Text(
                    text = "Название детали из заказа (кол-во. шт.)",
                    fontSize = 20.sp
                )
            }
        }
    }
}