package com.example.myhealth.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myhealth.PreferencesManager
import com.example.myhealth.presentation.bottomnav.BottomNavGraphScreen
import com.example.myhealth.presentation.onboarding.OnboardingScreen

@Composable
fun MyHealthApp() {
    val navController = rememberNavController()
    val prefs = remember { PreferencesManager(navController.context) }
    val startDestination = if (prefs.isOnboardingComplete()) "bottom_nav" else "onboarding"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("onboarding") {
            OnboardingScreen(
                onFinished = {
                    navController.navigate("bottom_nav") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }
        composable("bottom_nav") {
            BottomNavGraphScreen()
        }
    }
}