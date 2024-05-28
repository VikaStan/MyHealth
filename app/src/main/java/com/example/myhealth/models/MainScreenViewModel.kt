package com.example.myhealth.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.myhealth.ui.components.appbar.rememberAppBarState

class MainScreenViewModel: ViewModel() {
    //val navController = rememberNavController()
   // val appBarState = rememberAppBarState(navController)
    val diaryModel: DiaryViewModel=DiaryViewModel()
}