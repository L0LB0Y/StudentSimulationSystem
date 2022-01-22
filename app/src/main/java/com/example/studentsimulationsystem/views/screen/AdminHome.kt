package com.example.studentsimulationsystem.views.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.studentsimulationsystem.model.Student
import com.example.studentsimulationsystem.ui.theme.secondary
import com.example.studentsimulationsystem.ui.theme.spacing
import com.example.studentsimulationsystem.viewmodel.AdminViewModel

@Composable
fun AdminHome(adminViewModel: AdminViewModel) {
    LaunchedEffect(key1 = true ){
        adminViewModel.getListOfStudent()
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        Column(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            Text(
                text = "Select Students Year",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            val years =
                listOf("First Year", "Second Year", "Third Year", "Fourth Year", "Fifth Year")
            var selectedYears by remember {
                mutableStateOf("First Year")
            }
            LazyRow(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                items(years) { year ->
                    Card(
                        Modifier
                            .height(70.dp)
                            .width(100.dp)
                            .border(
                                width = 2.dp,
                                color = if (selectedYears == year) secondary else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                selectedYears = year

                            },
                        shape = RoundedCornerShape(12.dp),
                        backgroundColor = if (selectedYears == year) Color(0xFF8ECBE2) else
                            Color(0xFFAFD5E4)
                    ) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = year,
                                fontSize = 14.sp,
                                color = if (selectedYears == year) Color.White else secondary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            Text(
                text = "Chose Semester",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                val semester = listOf("Semester One", "Semester Tow")
                var selectedSemester by remember {
                    mutableStateOf("Semester One")
                }
                semester.forEach {
                    Card(
                        Modifier
                            .height(70.dp)
                            .weight(1f)
                            .border(
                                width = 2.dp,
                                color = if (selectedSemester == it) secondary else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                selectedSemester = it

                            },
                        shape = RoundedCornerShape(12.dp),
                        backgroundColor = if (selectedSemester == it) Color(0xFF8ECBE2) else
                            Color(0xFFAFD5E4)
                    ) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = it,
                                fontSize = 14.sp,
                                color = if (selectedSemester == it) Color.White else secondary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }


            }

        }

        Column(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            Text(
                text = "Students Result",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            val students by adminViewModel.students.observeAsState()
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                students?.let {
                    items(items = it) { student ->
                        StudentResultDetails(student = student)
                    }
                }
            }
        }


    }
}


@Composable
fun StudentResultDetails(
    student: Student
) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(
                bottom = MaterialTheme.spacing.small
            ), shape = RoundedCornerShape(MaterialTheme.spacing.extraSmall), elevation = 5.dp
    ) {
        Column {
            Row(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = MaterialTheme.spacing.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(0.6f),
                    text = "Name: ${student.studentName}", fontSize = 14.sp,
                    color = secondary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(Color(0xFFebebeb))
                )
                Text(
                    modifier = Modifier
                        .padding(start = MaterialTheme.spacing.small)
                        .weight(0.4f),
                    text = "ID: ${student.studentID}", fontSize = 14.sp,
                    color = Color(0xFFaaaaaa),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFebebeb))
            )
            student.subject.forEach { subjectsResult ->
                Row(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(start = MaterialTheme.spacing.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(0.6f),
                        text = "Subject: ${subjectsResult.subjectName}", fontSize = 14.sp,
                        color = secondary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(Color(0xFFebebeb))
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = MaterialTheme.spacing.small)
                            .weight(0.4f),
                        text = "Degree: ${subjectsResult.subjectDegree}", fontSize = 14.sp,
                        color = Color(0xFFaaaaaa),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFebebeb))
                )
            }
        }
    }
}