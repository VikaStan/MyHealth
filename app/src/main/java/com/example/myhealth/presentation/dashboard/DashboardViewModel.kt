package com.example.myhealth.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealth.data.repository.ProfileRepository
import com.example.myhealth.data.repository.StepsRepository
import com.example.myhealth.domain.repository.HealthRepository
import com.example.myhealth.domain.repository.MealTimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val healthRepo: HealthRepository,
    private val stepsRepo: StepsRepository,
    private val mealRepo: MealTimeRepository,
    private val profileRepo: ProfileRepository
) : ViewModel() {

    val uiState = combine(
        healthRepo.todayStats(),
        stepsRepo.getStepsForLastWeek(),
        mealRepo.getTodayCalories(),
        profileRepo.profileFlow
    ) { stats, week, calories, profile ->
        DashboardState(
            steps = stats.steps,
            waterDrunk = stats.waterDrunk.toInt(),
            caloriesBurned = calories,
            weeklySteps = week,
            waterTarget = stats.dailyTargetWater.toInt(),
            caloriesTarget = profile.caloriesTarget
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, DashboardState())

    fun addWater() = viewModelScope.launch { healthRepo.addWater(200f) }
}