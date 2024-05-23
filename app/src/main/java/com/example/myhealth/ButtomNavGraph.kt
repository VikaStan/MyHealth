package com.example.myhealth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myhealth.Screens.Account
import com.example.myhealth.Screens.Diary
import com.example.myhealth.Screens.Screen
import com.example.myhealth.Screens.Settings
import com.example.myhealth.Screens.Stats
import com.example.myhealth.ui.components.DatePickerWithDialog


@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier= Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Diary.route,
        modifier = modifier
    ) {
        composable(route = Screen.Diary.route) {
            Diary(navHostController = navController)
        }
        composable(route = Screen.Stats.route) {
            Stats("Stats")
        }
        composable(route = Screen.Account.route) {
            Account("Account")
        }
        composable(route = Screen.Settings.route) {
            Settings(onBackClick = {navController.popBackStack() })
        }
        composable(route = Screen.FoodAdd.route) {
            Settings(onBackClick = {navController.popBackStack() })
        }
        composable(route = Screen.SleepAdd.route) {
            Settings(onBackClick = {navController.popBackStack() })
        }

    }
}