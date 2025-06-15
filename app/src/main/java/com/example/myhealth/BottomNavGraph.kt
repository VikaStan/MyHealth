package com.example.myhealth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myhealth.screens.Account
import com.example.myhealth.screens.DiaryScreen
import com.example.myhealth.screens.FoodAdd
import com.example.myhealth.screens.Screen
import com.example.myhealth.screens.Settings
import com.example.myhealth.screens.SleepAdd
import com.example.myhealth.screens.Stats
import com.example.myhealth.models.MainScreenViewModel
import com.example.myhealth.presentation.onboarding.OnboardingScreen
import com.example.myhealth.presentation.dashboard.DashBoardScreen


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
        startDestination = "onboarding",
        modifier = modifier
    ) {
        composable(route = Screen.Diary.route) {
            DiaryScreen(navHostController = navController, model = mainScreenViewModel.diaryModel, mainModel = mainScreenViewModel)
        }
        composable(route = Screen.Stats.route) {
            Stats(mainScreenViewModel)
        }
        composable(route = Screen.Account.route) {
            Account()
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
        composable(Screen.Onboarding.route) {
            OnboardingScreen { navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Onboarding.route) { inclusive = true }
            } }
        }
        composable(route = Screen.Dashboard.route) {
            DashBoardScreen()
        }


    }
}


