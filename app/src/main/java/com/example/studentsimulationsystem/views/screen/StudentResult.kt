package com.example.studentsimulationsystem.views.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentsimulationsystem.ui.theme.secondary
import com.example.studentsimulationsystem.ui.theme.spacing
import com.example.studentsimulationsystem.utiles.TypeOfUser

@Composable
fun StudentResult() {
    var isMaster by remember {
        mutableStateOf(true)
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        MainRow()
        val student = TypeOfUser.student
        student?.let {
            it.subject.forEach { subject ->
                DataRow(
                    isMaster = isMaster,
                    subjectName = subject.subjectName,
                    degree = subject.subjectDegree,
                    numberOfHours = "3"
                )
                isMaster = !isMaster
            }
        }
        DegreeRow()

    }
}

@Composable
fun DegreeRow() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(MaterialTheme.spacing.extraLarge)
            .clip(
                RoundedCornerShape(MaterialTheme.spacing.small)
            )
            .background(Color(0xFF3BE615))
            .padding(start = MaterialTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Succeed", fontSize = 20.sp,
            color = secondary,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun MainRow(
    subjectName: String = "Subject Name",
    numberOfHours: String = "Hours",
    degree: String = "Degree"
) {
    Card(
        Modifier
            .fillMaxWidth()

            .padding(
                top = MaterialTheme.spacing.small,
                bottom = MaterialTheme.spacing.small
            ), shape = RoundedCornerShape(MaterialTheme.spacing.extraSmall), elevation = 5.dp
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(MaterialTheme.spacing.extraLarge)
                .padding(start = MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = subjectName, fontSize = 14.sp,
                color = secondary,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(secondary)
            )
            Text(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = numberOfHours, fontSize = 14.sp,
                color = secondary,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(secondary)
            )
            Text(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = degree, fontSize = 14.sp,
                color = secondary,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun DataRow(
    isMaster: Boolean = false,
    subjectName: String = "Database",
    numberOfHours: String = "5",
    degree: String = "A+"
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(MaterialTheme.spacing.extraLarge)
            .clip(
                RoundedCornerShape(MaterialTheme.spacing.small)
            )
            .background(if (isMaster) Color(0xFF8ECBE2) else Color(0xFFAFD5E4))
            .padding(start = MaterialTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = subjectName, fontSize = 14.sp,
            color = secondary,
            fontWeight = FontWeight.Medium
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(secondary)
        )
        Text(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = numberOfHours, fontSize = 14.sp,
            color = secondary,
            fontWeight = FontWeight.Medium
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(secondary)
        )
        Text(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = degree, fontSize = 14.sp,
            color = secondary,
            fontWeight = FontWeight.Medium
        )
    }
}
