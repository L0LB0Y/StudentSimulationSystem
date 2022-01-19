package com.example.studentsimulationsystem.views.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studentsimulationsystem.R
import com.example.studentsimulationsystem.ui.theme.primary
import com.example.studentsimulationsystem.ui.theme.secondary
import com.example.studentsimulationsystem.ui.theme.spacing
import kotlin.random.Random

@Composable
fun StudentHome(navController: NavHostController) {
    Column(
        Modifier
            .padding(top = MaterialTheme.spacing.small)
            .fillMaxSize()
            .background(color = primary)
            .padding(start = MaterialTheme.spacing.medium, end = MaterialTheme.spacing.medium)
    ) {
        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth()
        ) {
            NumberOneInBox()
        }
        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth()
        ) {
            NumberTowInBox(navController)
        }
        Box(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.medium)
                    .fillMaxSize(), elevation = MaterialTheme.spacing.default
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.result),
                    contentDescription = "Student"
                )
            }
        }


    }
}

@Composable
fun NumberTowInBox(navController: NavHostController) {
    val category = listOf("Assignment", "Courses", "Result", "Attendees")
    val random = Random(1)
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        for (i in 0..category.lastIndex step 2)
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                CardWithData(categoryName = category[i], random.nextFloat()) {
                    if (category[i] == "Result")
                        navController.navigate("StudentResult")
                    else
                        Toast.makeText(context, "Soon !!", Toast.LENGTH_SHORT).show()
                }
                if (i + 1 <= category.lastIndex) {
                    CardWithData(categoryName = category[i + 1], random.nextFloat()) {
                        Toast.makeText(context, "Soon !!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}

@Composable
fun RowScope.CardWithData(categoryName: String, progress: Float, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(MaterialTheme.spacing.small),
        backgroundColor = Color(0xFFebebeb)
    ) {
        Column(
            Modifier
                .padding(
                    MaterialTheme.spacing.small
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = categoryName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                color = Color(0xFFaaaaaa)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(6.dp)
                    .clip(RoundedCornerShape(MaterialTheme.spacing.small))
                    .background(color = Color(0xFF00AEEF))
            )
        }

    }
}

@Composable
fun NumberOneInBox(userName: String = "Make") {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        Text(text = "Hello, $userName", fontSize = 16.sp, color = Color(0xFFaaaaaa))
        Text(
            text = "Al Neelain University",
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        var textFieldValue by remember {
            mutableStateOf("")
        }
        TextField(
            modifier = Modifier
                .padding(bottom = MaterialTheme.spacing.small)
                .fillMaxWidth()
                .height(TextFieldDefaults.MinHeight),
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
            }, trailingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.spacing.small,
                            end = MaterialTheme.spacing.small,
                            bottom = MaterialTheme.spacing.small
                        )
                        .clip(shape = RoundedCornerShape(MaterialTheme.spacing.small))
                        .size(40.dp)
                        .background(secondary)
                        .clickable { },
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            placeholder = {
                Text(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    text = "Search",
                    fontSize = 16.sp, color = secondary
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(MaterialTheme.spacing.small),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFebebeb),
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
        Text(text = "Categories", fontSize = 20.sp, color = Color.Black)
    }
}