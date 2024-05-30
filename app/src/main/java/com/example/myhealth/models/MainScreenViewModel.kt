package com.example.myhealth.models

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
) : ViewModel() {

    lateinit var diaryModel: DiaryViewModel
    lateinit var foodAddViewModel: FoodAddViewModel
    fun initiate(diaryModel: DiaryViewModel, foodAddViewModel: FoodAddViewModel){
        this.diaryModel=diaryModel
        this.foodAddViewModel = foodAddViewModel
    }

    //val navController = rememberNavController()
    // val appBarState = rememberAppBarState(navController)
    //val diaryModel: DiaryViewModel= hiltViewModel()
    //val foodAddViewModel: DiaryViewModel= hiltViewModel()
}