package com.example.myhealth.models

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
) : ViewModel() {

    lateinit var diaryModel: DiaryViewModel
    lateinit var foodAddViewModel: FoodAddViewModel
    lateinit var sleepAddViewModel: SleepAddViewModel
    lateinit var statsViewModel: StatsViewModel
    fun initiate(diaryModel: DiaryViewModel, foodAddViewModel: FoodAddViewModel, sleepAddViewModel: SleepAddViewModel, statsViewModel: StatsViewModel){
        this.diaryModel= diaryModel
        this.foodAddViewModel = foodAddViewModel
        this.sleepAddViewModel = sleepAddViewModel
        this.statsViewModel = statsViewModel
    }

    //val navController = rememberNavController()
    // val appBarState = rememberAppBarState(navController)
    //val diaryModel: DiaryViewModel= hiltViewModel()
    //val foodAddViewModel: DiaryViewModel= hiltViewModel()
}