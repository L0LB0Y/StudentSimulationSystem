package com.example.studentsimulationsystem.views.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.studentsimulationsystem.ui.theme.secondary
import com.example.studentsimulationsystem.ui.theme.spacing
import com.example.studentsimulationsystem.viewmodel.AdminViewModel
import com.example.studentsimulationsystem.views.component.CustomTextField
import com.example.studentsimulationsystem.views.component.StudentSemesterRow
import com.example.studentsimulationsystem.views.component.StudentYearsRow

@Composable
fun AddNewStudent(adminViewModel: AdminViewModel) {
    var showingAddScreen by remember {
        mutableStateOf(false)
    }
    val buttonText = if (showingAddScreen) "Save Student" else "Add Student"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        if (!showingAddScreen)
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
            }
        if (showingAddScreen)
            InsertNewStudent(adminViewModel = adminViewModel)
        Button(
            onClick = {
                showingAddScreen = !showingAddScreen
                if (buttonText == "Save Student" && showingAddScreen)
                    adminViewModel.insertStudentInServer()
            },
            modifier = Modifier
                .padding(
                    start = if (showingAddScreen) MaterialTheme.spacing.medium else MaterialTheme.spacing.default,
                    end = if (showingAddScreen) MaterialTheme.spacing.medium else MaterialTheme.spacing.default
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
fun ColumnScope.InsertNewStudent(
    adminViewModel: AdminViewModel,
    mainText: String = "Add New Student"
) {
    Box(modifier = Modifier.weight(0.9f), contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier
                .padding(
                    top = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium,
                    start = MaterialTheme.spacing.medium
                ),
            color = Color(0xFFE0DEDE),
            shape = RoundedCornerShape(MaterialTheme.spacing.small),
            border = BorderStroke(
                width = MaterialTheme.spacing.extraSmall.div(2),
                color = secondary
            ),
            elevation = MaterialTheme.spacing.medium
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = mainText,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                TowTextField(
                    "Student Name",
                    onTextOneStateValueChange = { adminViewModel.studentName.value = it },
                    onTextTowStateValueChange = { }, showingTextTow = false
                )
                TowTextField(
                    "Student ID",
                    onTextOneStateValueChange = { adminViewModel.studentID.value = it },
                    onTextTowStateValueChange = { }, showingTextTow = false
                )
                TowTextField(
                    "AI",
                    "Database",
                    onTextOneStateValueChange = { adminViewModel.ai.value = it },
                    onTextTowStateValueChange = { adminViewModel.database.value = it })
                TowTextField(
                    "Data Structure",
                    "Network",
                    onTextOneStateValueChange = { adminViewModel.dataStructure.value = it },
                    onTextTowStateValueChange = { adminViewModel.network.value = it })
                TowTextField(
                    "Programming",
                    onTextOneStateValueChange = { adminViewModel.programming.value = it },
                    onTextTowStateValueChange = { }, showingTextTow = false
                )
            }
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
