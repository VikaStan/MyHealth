package com.example.myhealth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myhealth.Screens.Account
import com.example.myhealth.Screens.Diary
import com.example.myhealth.Screens.FoodAdd
import com.example.myhealth.Screens.Screen
import com.example.myhealth.Screens.Settings
import com.example.myhealth.Screens.SleepAdd
import com.example.myhealth.Screens.Stats
import com.example.myhealth.models.DiaryViewModel
import com.example.myhealth.models.FoodAddViewModel
import com.example.myhealth.models.MainScreenViewModel


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel(),
    start:String = Screen.Diary.route
) {
    NavHost(
        navController = navController,
        startDestination = start,
        modifier = modifier
    ) {
        composable(route = Screen.Diary.route) {
            Diary(navHostController = navController, model = mainScreenViewModel.diaryModel, mainModel = mainScreenViewModel)
        }
        composable(route = Screen.Stats.route) {
            Stats(mainScreenViewModel)
        }
        composable(route = Screen.Account.route) {
            Account(mainScreenViewModel)
        }
        composable(route = Screen.Settings.route) {
            Settings(onBackClick = { navController.popBackStack() })
        }
        composable(route = Screen.FoodAdd.route + "/{foodType}",
            arguments = listOf(navArgument("foodType") { type = NavType.StringType })) {
            FoodAdd(it.arguments?.getString("foodType"), modifier, mainScreenViewModel.diaryModel)
        }
        composable(route = Screen.SleepAdd.route) {
            SleepAdd(modifier, mainScreenViewModel.diaryModel, mainScreenViewModel.sleepAddViewModel)
        }

    }
}