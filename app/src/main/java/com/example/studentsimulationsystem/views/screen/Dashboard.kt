package com.example.studentsimulationsystem.views.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentsimulationsystem.R
import com.example.studentsimulationsystem.ui.theme.primary
import com.example.studentsimulationsystem.ui.theme.secondary
import com.example.studentsimulationsystem.ui.theme.spacing
import com.example.studentsimulationsystem.utiles.TypeOfUser
import kotlinx.coroutines.launch

@Composable
fun Dashboard(ParentNavController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .background(primary),
        scaffoldState = scaffoldState,
        topBar = {
            DashboardTopBar {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        drawerContent = {
            DashboardDrawer {
                scope.launch {
                    scaffoldState.drawerState.close()
                }
                when (it) {
                    "Logout" -> {
                        ParentNavController.popBackStack()
                        ParentNavController.navigate("login")
                    }
                    "Result" -> {
                        if (TypeOfUser.UserType == "Admin")
                            navController.navigate("AdminResult")
                        else
                            navController.navigate("StudentResult")
                    }
                    "Home" -> {
                        if (TypeOfUser.UserType == "Admin") {
                            if (navController.currentBackStackEntry?.id != "AdminHome")
                                navController.navigate("AdminHome")
                        } else
                            if (navController.currentBackStackEntry?.id != "StudentHome")
                                navController.navigate("StudentHome")
                    }
                    else ->
                        Toast.makeText(context, "Soon !!", Toast.LENGTH_SHORT).show()
                }
            }
        },
        drawerBackgroundColor = Color.White,
        drawerElevation = MaterialTheme.spacing.default,
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerShape = RoundedCornerShape(size = MaterialTheme.spacing.default)
    ) {
        val startDestination = if (TypeOfUser.UserType == "Admin") "AdminHome" else "StudentHome"
        NavHost(navController = navController, startDestination = startDestination) {
            composable("StudentHome") {
                StudentHome(navController)
            }
            composable("StudentResult") {
                StudentResult()
            }
            composable("AdminHome") {
                AdminHome()
            }
            composable("AdminResult") {
                AdminResult()
            }
        }
    }
}

@Composable
fun DashboardTopBar(onDrawerClicked: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(primary)
            .height(60.dp)
            .padding(
                top = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.small,
                start = MaterialTheme.spacing.small
            ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        Icon(
            modifier = Modifier
                .size(45.dp)
                .clickable { onDrawerClicked() },
            imageVector = Icons.Default.Menu,
            contentDescription = "hamburger Icon", tint = secondary
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier
                .width(45.dp)
                .height(45.dp)
                .clip(CircleShape)
                .border(width = 1.dp, color = secondary, shape = CircleShape),
            contentScale = ContentScale.Crop,
            painter = painterResource(
                id = if (TypeOfUser.UserType == "Admin") R.drawable.teacher_icon
                else
                    R.drawable.ic_student_icon
            ),
            contentDescription = "User Logo"
        )

    }
}

@Composable
fun DashboardDrawer(onDrawerClicked: (selectedText: String) -> Unit) {
    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DrawerUserProfile()
        DrawerContent {
            onDrawerClicked(it)
        }
    }

}


@Composable
fun ColumnScope.DrawerUserProfile(
    userName: String = "Mohammed Make",
    userID: String = "44586296956"
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(0.4f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .width(180.dp)
                    .height(180.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                painter = painterResource(
                    id = if (TypeOfUser.UserType == "Admin") R.drawable.teacher_icon
                    else
                        R.drawable.ic_student_icon
                ),
                contentDescription = "Student Logo"
            )

            Text(
                text = if (TypeOfUser.UserType == "Admin") "Teacher: $userName" else "Student: $userName",
                color = secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = userID, color = Color(0xFF5e5e5e),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ColumnScope.DrawerContent(onDrawerClicked: (selectedText: String) -> Unit) {
    var selectedText by remember {
        mutableStateOf("Home")
    }
    val category = listOf("Home", "User Profile", "Result", "Settings", "Logout")
    Column(
        Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
            .weight(0.6f)
    ) {
        category.forEach { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (selectedText == category) Color(0xFFf7f7f7) else Color(
                            0xFFFFFFFF
                        )
                    )
                    .height(56.dp)
                    .clickable {
                        selectedText = category
                        onDrawerClicked(selectedText)
                    }, verticalAlignment = CenterVertically
            ) {
                if (selectedText == category) {
                    Spacer(
                        modifier = Modifier
                            .background(secondary)
                            .width(3.dp)
                            .height(56.dp)
                    )
                }
                Text(
                    modifier = Modifier.padding(start = MaterialTheme.spacing.medium),
                    text = category, fontSize = 16.sp,
                    fontWeight = if (selectedText == category) FontWeight.Bold else FontWeight.Medium,
                    color = Color(0xFFaaaaaa)
                )
            }

        }

    }
}