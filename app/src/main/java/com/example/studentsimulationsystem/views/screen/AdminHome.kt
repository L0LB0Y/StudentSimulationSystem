package com.example.studentsimulationsystem.views.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentsimulationsystem.model.Student
import com.example.studentsimulationsystem.ui.theme.secondary
import com.example.studentsimulationsystem.ui.theme.spacing
import com.example.studentsimulationsystem.viewmodel.AdminViewModel
import com.example.studentsimulationsystem.views.component.StudentSemesterRow
import com.example.studentsimulationsystem.views.component.StudentYearsRow

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

        Text(
            text = "Students Result",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        val students by adminViewModel.students.observeAsState()
        students?.let { ShowingListOfStudentResults(it) }
    }
}

@Composable
fun ColumnScope.ShowingListOfStudentResults(students: List<Student>) {
    LazyColumn(
        modifier = Modifier
            .weight(0.6f)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        items(items = students) { student ->
            StudentResultDetails(student = student)
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
            .padding(bottom = MaterialTheme.spacing.small),
        shape = RoundedCornerShape(MaterialTheme.spacing.extraSmall),
        elevation = 5.dp,
        backgroundColor = Color(0xFFE0DEDE)
    ) {
        Column {

            StudentResultDetailsMainInfoRow(
                "Name: ${student.studentName}",
                "ID: ${student.studentID}"
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFebebeb))
            )

            student.subject.forEach { subjectsResult ->
                StudentResultDetailsMainInfoRow(
                    str1 = "Subject: ${subjectsResult.subjectName}",
                    str2 = "Degree: ${subjectsResult.subjectDegree}"
                )
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

@Composable
fun ColumnScope.StudentResultDetailsMainInfoRow(str1: String, str2: String) {
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
            text = str1, fontSize = 14.sp,
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
            text = str2, fontSize = 14.sp,
            color = Color(0xFFaaaaaa),
            fontWeight = FontWeight.Bold
        )
    }
}
