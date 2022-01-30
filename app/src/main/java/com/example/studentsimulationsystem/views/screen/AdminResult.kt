package com.example.studentsimulationsystem.views.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.studentsimulationsystem.model.Student
import com.example.studentsimulationsystem.ui.theme.secondary
import com.example.studentsimulationsystem.ui.theme.spacing
import com.example.studentsimulationsystem.viewmodel.AdminViewModel
import com.example.studentsimulationsystem.views.component.StudentSemesterRow
import com.example.studentsimulationsystem.views.component.StudentYearsRow

@Composable
fun AdminResult(adminViewModel: AdminViewModel) {
    var isClicked by remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {

        Text(
            text = "Select Students Year",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        val years = adminViewModel.years
        StudentYearsRow(years)

        Text(
            text = "Chose Semester",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        val semester = adminViewModel.semester
        StudentSemesterRow(semester)

        Column(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            Text(
                text = "Update Students Result",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                var textFieldValue by remember {
                    mutableStateOf("")
                }

                TextField(
                    modifier = Modifier
                        .weight(0.7f)
                        .height(TextFieldDefaults.MinHeight),
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it
                    },
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                    placeholder = {
                        Text(
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                            text = "Enter Student ID",
                            fontSize = 16.sp, color = Color(0xFFaaaaaa)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFebebeb),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Button(
                    onClick = {
                        adminViewModel.getStudentByID(textFieldValue) {
                            isClicked = true
                        }

                    },
                    modifier = Modifier
                        .weight(0.3f)
                        .height(TextFieldDefaults.MinHeight),
                    shape = RoundedCornerShape(MaterialTheme.spacing.small),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3BE615))
                ) {
                    Text(
                        modifier = Modifier,
                        text = if (isClicked) "Submit" else "Check",
                        fontSize = 14.sp, color = Color.White
                    )
                }
            }
            if (isClicked) {
                val student by adminViewModel.student.observeAsState()
                student?.let { InsetStudentResultDetails(it) }
            }
        }
    }
}

@Composable
fun InsetStudentResultDetails(student: Student) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(bottom = MaterialTheme.spacing.small),
        shape = RoundedCornerShape(MaterialTheme.spacing.extraSmall), elevation = 5.dp
    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
                    .padding(start = MaterialTheme.spacing.small),
                verticalAlignment = CenterVertically
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
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            ) {
                student.subject.forEach { subjectsResult ->
                    item {
                        Row(
                            Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(
                                    start = MaterialTheme.spacing.small,
                                    top = MaterialTheme.spacing.small
                                ),
                            verticalAlignment = CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(0.7f),
                                text = "Subject: ${subjectsResult.subjectName}", fontSize = 14.sp,
                                color = secondary,
                                fontWeight = FontWeight.Bold
                            )
                            var textFieldValueTow by remember {
                                mutableStateOf("")
                            }
                            TextField(
                                modifier = Modifier
                                    .weight(0.3f)
                                    .height(TextFieldDefaults.MinHeight),
                                value = textFieldValueTow,
                                onValueChange = {
                                    textFieldValueTow = it
                                },
                                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                                placeholder = {
                                    Text(
                                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                                        text = subjectsResult.subjectDegree,
                                        fontSize = 16.sp, color = Color(0xFFaaaaaa)
                                    )
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color(0xFFebebeb),
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                    }
                }
            }
        }
    }
}
