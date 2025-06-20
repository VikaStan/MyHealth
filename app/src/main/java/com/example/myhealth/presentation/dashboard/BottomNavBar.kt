package com.example.myhealth.presentation.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myhealth.ui.theme.LightGreen

@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(24.dp))
    ) {
        val items = listOf("dashboard", "diary", "stats", "profile")
        items.forEach { route ->
            NavigationBarItem(
                selected = navController.currentBackStackEntry?.destination?.route == route,
                onClick = { navController.navigate(route) },
                icon = {
                    val icon = when (route) {
                        "dashboard" -> Icons.Default.Home
                        "diary" -> Icons.Default.Restaurant
                        "stats" -> Icons.Default.BarChart
                        else -> Icons.Default.Person
                    }
                    Icon(icon, null)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LightGreen,
                    indicatorColor = LightGreen,
                    selectedTextColor = LightGreen
                ),
                alwaysShowLabel = false
            )
        }
    }
}