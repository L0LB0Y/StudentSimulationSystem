package com.example.studentsimulationsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.studentsimulationsystem.ui.theme.StudentSimulationSystemTheme
import com.example.studentsimulationsystem.views.screen.ScreenHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentSimulationSystemTheme {
                /** Screen Holder Where The Entry Point Of The App*/
                ScreenHolder()
            }
        }
    }
}