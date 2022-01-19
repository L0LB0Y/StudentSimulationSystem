package com.example.studentsimulationsystem.views.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentsimulationsystem.ui.theme.primary

@Composable
fun ScreenHolder() {
    Column(
        Modifier
            .fillMaxSize()
            .background(primary)
    ) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "Splash") {
            composable("Splash") {
                SplashScreen(navController = navController)
            }
            composable("login") {
                Login(navController = navController)
            }
            composable("dashboard") {
                Dashboard(ParentNavController = navController)
            }
        }
    }
}