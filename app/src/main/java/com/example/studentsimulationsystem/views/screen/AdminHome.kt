package com.example.studentsimulationsystem.views.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.example.studentsimulationsystem.views.component.ShowDialog
import com.example.studentsimulationsystem.views.component.StudentSemesterRow
import com.example.studentsimulationsystem.views.component.StudentYearsRow

@Composable
fun AdminHome(adminViewModel: AdminViewModel) {
    LaunchedEffect(key1 = true) {
        adminViewModel.getListOfStudent()
    }
    var showingUpdateScreen by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            val isLoading by remember {
                adminViewModel.isLoading
            }
            if (isLoading)
                ShowDialog()

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
            students?.let {
                ShowingListOfStudentResults(it) { student->
                    adminViewModel.studentID.value = student.studentID
                    showingUpdateScreen = !showingUpdateScreen
                }
            }
        }
        if (showingUpdateScreen)
            UpdateStudent(viewModel = adminViewModel) {
                showingUpdateScreen = !showingUpdateScreen
                adminViewModel.getListOfStudent()
            }
    }
}

@Composable
fun ColumnScope.ShowingListOfStudentResults(
    students: List<Student>, onClick: (student: Student) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .weight(0.6f)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        items(items = students) { student ->
            StudentResultDetails(student = student) {
                onClick(it)
            }
        }
    }
}

@Composable
fun StudentResultDetails(
    student: Student, onClick: (student: Student) -> Unit
) {
    Card(
        Modifier
            .size(300.dp)
            .clickable { onClick(student) }
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

@Composable
fun UpdateStudent(viewModel: AdminViewModel, onCloseUpdatesScreen: () -> Unit) {
    Column(
        modifier = Modifier
            .background(Color(0xFF000000).copy(0.62f))
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {

        InsertNewStudent(viewModel, mainText = "Update Student") {
            onCloseUpdatesScreen()
        }
        Button(
            onClick = {
                viewModel.updateStudent{
                    onCloseUpdatesScreen()
                }
            },
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .fillMaxWidth()
                .weight(0.15f),
            shape = RoundedCornerShape(MaterialTheme.spacing.small),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3BE615))
        ) {
            Text(
                text = "Update Student",
                fontSize = 14.sp, color = Color.White
            )
        }
    }
}
