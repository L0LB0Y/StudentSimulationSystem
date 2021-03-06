package com.example.studentsimulationsystem.views.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.studentsimulationsystem.ui.theme.secondary
import com.example.studentsimulationsystem.ui.theme.spacing

/** This FUnction For Showing Dialog In User Screen */
@Composable
fun ShowDialog() {
    Dialog(
        onDismissRequest = { /**/ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ) {
        /** Loading dialog contents */
        /** Loading dialog contents */
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .clip(shape = MaterialTheme.shapes.small)
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 4.dp,
                color = secondary
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "Please wait",
                style = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
            )
        }
    }
}


@Composable
fun StudentSemesterRow(semesters: List<String>) {
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        var selectedSemester by remember {
            mutableStateOf("Semester One")
        }
        semesters.forEach { semester ->
            SemesterOrYearCard(
                name = semester,
                isSelected = selectedSemester == semester,
                onClick = { selectedSemester = semester }, modifier = Modifier.weight(1f)
            )
        }
    }

}

@Composable
fun StudentYearsRow(years: List<String>) {
    var selectedYears by remember {
        mutableStateOf("First Year")
    }
    LazyRow(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        items(years) { year ->
            SemesterOrYearCard(
                name = year,
                isSelected = selectedYears == year,
                onClick = { selectedYears = year }, modifier = Modifier.width(100.dp)
            )
        }
    }
}


@Composable
fun SemesterOrYearCard(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier
            .height(70.dp)
            .border(
                width = 2.dp,
                color = if (isSelected) secondary else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(12.dp),
        backgroundColor = if (isSelected) Color(0xFF8ECBE2) else
            Color(0xFFAFD5E4)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = name,
                fontSize = 14.sp,
                color = if (isSelected) Color.White else secondary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    textTitle: String,
    value: String,
    onValueChange: (newValue: String) -> Unit
) {
    TextField(
        modifier = modifier
            .height(TextFieldDefaults.MinHeight),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}