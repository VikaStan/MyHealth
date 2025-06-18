package com.example.myhealth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myhealth.models.MainScreenViewModel
import com.example.myhealth.presentation.dashboard.DashBoardScreen
import com.example.myhealth.presentation.onboarding.OnboardingScreen
import com.example.myhealth.presentation.statistics.StatisticsScreen
import com.example.myhealth.screens.Account
import com.example.myhealth.screens.DiaryScreen
import com.example.myhealth.screens.FoodAdd
import com.example.myhealth.screens.Screen
import com.example.myhealth.screens.Settings


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val prefs = remember(context) { PreferencesManager(context) }
    val startDestination =
        if (prefs.isOnboardingComplete()) Screen.Dashboard.route else Screen.Onboarding.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Diary.route) {
            DiaryScreen(navHostController = navController, model = mainScreenViewModel.diaryModel, mainModel = mainScreenViewModel)
        }
        composable(route = Screen.Stats.route) {
            StatisticsScreen()
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


