package com.example.studentsimulationsystem.views.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.studentsimulationsystem.ui.theme.secondary

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