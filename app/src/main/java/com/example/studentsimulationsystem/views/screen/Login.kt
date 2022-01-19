package com.example.studentsimulationsystem.views.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studentsimulationsystem.R
import com.example.studentsimulationsystem.ui.theme.primary
import com.example.studentsimulationsystem.ui.theme.secondary
import com.example.studentsimulationsystem.ui.theme.spacing
import com.example.studentsimulationsystem.viewmodel.LoginViewModel
import com.example.studentsimulationsystem.views.component.ShowDialog

@Composable
fun Login(navController: NavController, loginViewModel: LoginViewModel = hiltViewModel()) {
    Column(
        Modifier
            .padding(MaterialTheme.spacing.medium)
            .fillMaxSize()
            .background(primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .weight(0.4f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .width(180.dp)
                    .height(180.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.student_logo),
                contentDescription = "Student Logo"
            )
        }
        Box(
            modifier = Modifier
                .weight(0.3f),
        ) {
            BunchOfText()
        }
        Box(
            modifier = Modifier
                .weight(0.3f),
            contentAlignment = Alignment.Center
        ) {
            BottomContent(navController, loginViewModel)
        }

    }
}

@Composable
fun BottomContent(navController: NavController, loginViewModel: LoginViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        var textFieldValue by remember {
            mutableStateOf("")
        }
        var isError by remember {
            mutableStateOf(false)
        }

        val isLoading by remember {
            loginViewModel.isLoading
        }
        if (isLoading)
            ShowDialog()
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(TextFieldDefaults.MinHeight),
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                isError = it.length < 3 || it.length > 15
            }, isError = isError,
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            placeholder = {
                Text(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    text = "Enter Your ID",
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
                loginViewModel.checkUserType(userPassword = textFieldValue) {
                    navController.popBackStack()
                    navController.navigate("dashboard")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(TextFieldDefaults.MinHeight),
            shape = RoundedCornerShape(MaterialTheme.spacing.small),
            colors = ButtonDefaults.buttonColors(backgroundColor = secondary)
        ) {
            Text(
                modifier = Modifier,
                text = "Log In",
                fontSize = 16.sp, color = Color.White
            )
        }
    }
}

@Composable
fun BunchOfText() {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to", color = secondary,
            fontWeight = FontWeight(400),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
        Text(
            text = "Student Simulation System", color = Color(0xFF5e5e5e),
            fontWeight = FontWeight(700),
            fontSize = 20.sp,
        )
    }
}