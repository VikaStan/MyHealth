package com.example.myhealth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.myhealth.presentation.account.Account
import com.example.myhealth.presentation.dashboard.DashBoardScreen
import com.example.myhealth.presentation.diary.DiaryScreen
import com.example.myhealth.presentation.onboarding.OnboardingScreen
import com.example.myhealth.presentation.statistics.StatisticsScreen


fun NavGraphBuilder.bottomNavGraph(navController: NavHostController) {
    composable("dashboard") { DashBoardScreen(navController = navController) }
    composable("onboarding") {
        OnboardingScreen(onFinished = {
            navController.navigate("dashboard") {
                popUpTo("onboarding") { inclusive = true }
            }
        })
    }
    composable("diary") { DiaryScreen(navController = navController) }
    composable("stats") { StatisticsScreen(navController = navController) }
    composable("profile") { Account(navController = navController) }
}


