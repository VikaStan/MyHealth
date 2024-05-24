package com.example.myhealth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myhealth.Screens.Account
import com.example.myhealth.Screens.Diary
import com.example.myhealth.Screens.FoodAdd
import com.example.myhealth.Screens.Screen
import com.example.myhealth.Screens.Settings
import com.example.myhealth.Screens.SleepAdd
import com.example.myhealth.Screens.Stats
import com.example.myhealth.ui.components.DatePickerWithDialog


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
            FoodAdd("",modifier)
        }
        composable(route = Screen.SleepAdd.route) {
            SleepAdd("SleepAdd")
        }

    }
}