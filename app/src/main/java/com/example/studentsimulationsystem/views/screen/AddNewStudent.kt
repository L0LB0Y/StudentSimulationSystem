package com.example.studentsimulationsystem.views.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
fun AddNewStudent(adminViewModel: AdminViewModel) {
    Column {
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
        InsertNewStudent(adminViewModel = adminViewModel)
        Button(
            onClick = {

            },
            modifier = Modifier
                .height(TextFieldDefaults.MinHeight),
            shape = RoundedCornerShape(MaterialTheme.spacing.small),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3BE615))
        ) {
            Text(
                modifier = Modifier,
                text = "Submit",
                fontSize = 14.sp, color = Color.White
            )
        }
    }
}


@Composable
fun InsertNewStudent(adminViewModel: AdminViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium,
                start = MaterialTheme.spacing.medium
            ),
        color = Color.White,
        shape = RoundedCornerShape(
            topStart = MaterialTheme.spacing.small,
            topEnd = MaterialTheme.spacing.small
        ),
        elevation = MaterialTheme.spacing.medium
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)) {
            TowTextField(
                "Student Name",
                "Student ID",
                onTextOneStateValueChange = { adminViewModel.studentName.value = it },
                onTextTowStateValueChange = { adminViewModel.studentID.value = it })
            TowTextField(
                "AI",
                "Database",
                onTextOneStateValueChange = { adminViewModel.ai.value = it },
                onTextTowStateValueChange = { adminViewModel.database.value = it })
            TowTextField(
                "Data Structure",
                "Network",
                onTextOneStateValueChange = { adminViewModel.dataStrucuer.value = it },
                onTextTowStateValueChange = { adminViewModel.network.value = it })
            TowTextField(
                "Programming",
                onTextOneStateValueChange = { adminViewModel.programming.value = it },
                onTextTowStateValueChange = { }, showingTextTow = false
            )
        }
    }
}

@Composable
fun TowTextField(
    textOne: String = "",
    textTow: String = "",
    onTextOneStateValueChange: (newValue: String) -> Unit,
    onTextTowStateValueChange: (newValue: String) -> Unit,
    showingTextTow: Boolean = true
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        var textOneState by remember {
            mutableStateOf("")
        }
        var textTowState by remember {
            mutableStateOf("")
        }
        CustomTextField(
            modifier = Modifier.weight(1f),
            textTitle = textOne,
            value = textOneState,
            onValueChange = {
                textOneState = it
                onTextOneStateValueChange(it)
            })
        if (showingTextTow)
            CustomTextField(
                modifier = Modifier.weight(1f),
                textTitle = textTow,
                value = textTowState,
                onValueChange = {
                    textTowState = it
                    onTextTowStateValueChange(it)
                })
    }
}
