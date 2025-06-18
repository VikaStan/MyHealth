package com.example.myhealth.presentation.statistics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealth.data.repository.StepsRepository
import com.example.myhealth.domain.models.Stats
import com.example.myhealth.domain.repository.HealthRepository
import com.example.myhealth.domain.repository.MealTimeRepository
import com.example.myhealth.domain.repository.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val healthRepo: HealthRepository,
    private val stepsRepo: StepsRepository,
    private val mealsRepo: MealTimeRepository,
    private val waterRepo: WaterRepository
) : ViewModel() {

    var selectedTab by mutableStateOf(StatsTab.ACTIVITY)
        private set

    val stats: StateFlow<Stats> = healthRepo.todayStats()
        .stateIn(viewModelScope, SharingStarted.Lazily, Stats())

    // Шаги за последние 7 дней
    val stepsData: StateFlow<List<Int>> = stepsRepo.getStepsForLastWeek()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Калории за последние 7 дней
    val caloriesData: StateFlow<List<Int>>
        get() = mealsRepo.getCaloriesForLastWeek()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Вода за последние 7 дней
    val waterData: StateFlow<List<Int>> = waterRepo.getWaterForLastWeek()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val caloriesToday: StateFlow<Int> = mealsRepo.getTodayCalories()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    // Суммарные БЖУ за сегодня
    val proteinsToday: StateFlow<Int> = mealsRepo.getProteinsToday()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)
    val fatsToday: StateFlow<Int> = mealsRepo.getFatsToday()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)
    val carbsToday: StateFlow<Int> = mealsRepo.getCarbsToday()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun selectTab(tab: StatsTab) {
        selectedTab = tab
    }
}

