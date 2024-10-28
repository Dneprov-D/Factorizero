package com.hfad.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.designsystem.components.theme.FactorizeroTheme
import com.hfad.designsystem.components.theme.FzBackground
import com.hfad.designsystem.components.theme.OnPrimaryDark
import com.hfad.ui.R

@Composable
fun EmployeeCard() {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.Gray, shape = CircleShape),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(25.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Имя Фамилия",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Должность",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLight() {
    FactorizeroTheme(darkTheme = false) {
        EmployeeCard()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDark() {
    FactorizeroTheme(darkTheme = true) {
        EmployeeCard()
    }
}