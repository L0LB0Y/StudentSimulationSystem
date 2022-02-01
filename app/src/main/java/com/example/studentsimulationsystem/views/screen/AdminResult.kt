package com.example.studentsimulationsystem.views.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.studentsimulationsystem.ui.theme.spacing
import com.example.studentsimulationsystem.viewmodel.AdminViewModel
import com.example.studentsimulationsystem.views.component.CustomTextField
import com.example.studentsimulationsystem.views.component.StudentSemesterRow
import com.example.studentsimulationsystem.views.component.StudentYearsRow

@Composable
fun AdminResult(adminViewModel: AdminViewModel) {
    var showingUpdateScreen by remember {
        mutableStateOf(false)
    }
    val buttonText = if (showingUpdateScreen) "Save Student" else "Update Student"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        if (!showingUpdateScreen)
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(0.9f),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
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
                    text = "Insert New Student",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                var studentID by remember {
                    adminViewModel.studentID
                }
                CustomTextField(
                    textTitle = "Enter Student ID",
                    value = studentID,
                    onValueChange = {
                        studentID = it
                        adminViewModel.studentID.value = it
                    })
            }
        if (showingUpdateScreen)
            UpdateStudent(adminViewModel)
        Button(
            onClick = {
                showingUpdateScreen = !showingUpdateScreen
                if (buttonText == "Save Student" && showingUpdateScreen)
                    adminViewModel.updateStudent()
            },
            modifier = Modifier
                .padding(
                    start = if (showingUpdateScreen) MaterialTheme.spacing.medium else MaterialTheme.spacing.default,
                    end = if (showingUpdateScreen) MaterialTheme.spacing.medium else MaterialTheme.spacing.default
                )
                .fillMaxWidth()
                .weight(0.1f),
            shape = RoundedCornerShape(MaterialTheme.spacing.small),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3BE615))
        ) {
            Text(
                text = buttonText,
                fontSize = 14.sp, color = Color.White
            )
        }
    }
}

@Composable
fun ColumnScope.UpdateStudent(viewModel: AdminViewModel) {
    InsertNewStudent(viewModel, mainText = "Update Student")
}
