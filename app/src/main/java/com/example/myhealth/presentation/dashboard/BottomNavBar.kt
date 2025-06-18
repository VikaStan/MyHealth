package com.example.myhealth.presentation.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar(containerColor = androidx.compose.ui.graphics.Color.White) {
        val items = listOf("home", "food", "stats", "profile")
        items.forEach { route ->
            NavigationBarItem(
                selected = navController.currentBackStackEntry?.destination?.route == route,
                onClick = { navController.navigate(route) },
                icon = {
                    val icon = when (route) {
                        "home" -> Icons.Default.Home
                        "food" -> Icons.Default.Restaurant
                        "stats" -> Icons.Default.BarChart
                        else -> Icons.Default.Person
                    }
                    Icon(icon, null)
                },
                alwaysShowLabel = false
            )
        }
    }
}