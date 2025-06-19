package com.example.myhealth.presentation.bottomnav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.myhealth.navigation.bottomNavGraph
import com.example.myhealth.presentation.dashboard.BottomNavBar
import com.example.myhealth.ui.theme.BackBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavGraphScreen() {
    val navController = rememberNavController()

    Scaffold(
        containerColor = BackBlue,
        bottomBar = { BottomNavBar(navController) }
    ) { inner ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(inner)
        ) {
            bottomNavGraph(navController)
        }
    }
}