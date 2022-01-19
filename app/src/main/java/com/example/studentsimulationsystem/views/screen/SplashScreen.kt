package com.example.studentsimulationsystem.views.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.studentsimulationsystem.R
import com.example.studentsimulationsystem.ui.theme.primary

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        Modifier
            .fillMaxSize()
            .background(primary),
        contentAlignment = Alignment.Center,
    ) {

        /** Lotti file location specified */
        val composition =
            rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.student_loading))

        /** Add extra properties to lottie file object*/
        val progress = animateLottieCompositionAsState(composition.value)
        /** Lotti File Animation*/
        LottieAnimation(
            composition.value,
            progress.value
        )

        /** Navigate to next screen after lotti file complete*/
        if (progress.progress == 1.0f) {
            LaunchedEffect(key1 = true) {
                navController.popBackStack()
                navController.navigate("login")
            }
        }

    }
}