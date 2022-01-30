package com.example.studentsimulationsystem.views.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentsimulationsystem.ui.theme.secondary
import com.example.studentsimulationsystem.ui.theme.spacing
import com.example.studentsimulationsystem.viewmodel.AdminViewModel
import com.example.studentsimulationsystem.views.component.ShowDialog

@Composable
fun AddNewStudent(adminViewModel: AdminViewModel) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        item {
            Column(
                modifier = Modifier
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
                    listOf(
                        "First Year",
                        "Second Year",
                        "Third Year",
                        "Fourth Year",
                        "Fifth Year"
                    )
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
        }
        item {
            Column(
                modifier = Modifier
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
        }
        item {
            val isLoading by remember {
                adminViewModel.isLoading
            }
            if (isLoading)
                ShowDialog()
            val dataStructure = remember {
                mutableStateOf("")
            }
            val programming = remember {
                mutableStateOf("")
            }
            val network = remember {
                mutableStateOf("")
            }
            val ai = remember {
                mutableStateOf("")
            }
            val database = remember {
                mutableStateOf("")
            }
            val studentID = remember {
                mutableStateOf("")
            }
            val studentName = remember {
                mutableStateOf("")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = "Insert New Student",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = {
                            adminViewModel.insertStudentInServer(
                                studentID = studentID.value,
                                studentName = studentName.value,
                                database = database.value,
                                ai = ai.value,
                                network = network.value,
                                dataStructure = dataStructure.value,
                                programming = programming.value
                            ) {
                                studentID.value = ""
                                studentName.value = ""
                                ai.value = ""
                                dataStructure.value = ""
                                database.value = ""
                                network.value = ""
                                programming.value = ""
                            }

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

                CustomTextField("Student Name", studentName)

                CustomTextField("Student ID", studentID)

                CustomTextField("Database", database)

                CustomTextField("AI", ai)

                CustomTextField("Network", network)

                CustomTextField("Programming", programming)

                CustomTextField("Data Structure", dataStructure)
            }
        }
    }
}

@Composable
fun CustomTextField(textTitle: String, textFieldValue: MutableState<String>) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(TextFieldDefaults.MinHeight),
        value = textFieldValue.value,
        onValueChange = {
            textFieldValue.value = it
        },
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
        placeholder = {
            Text(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                text = textTitle,
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