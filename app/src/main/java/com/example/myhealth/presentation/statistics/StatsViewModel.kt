package com.example.myhealth.presentation.statistics

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealth.data.repository.StepsRepository
import com.example.myhealth.domain.repository.MealTimeRepository
import com.example.myhealth.domain.repository.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val stepsRepo: StepsRepository,
    private val mealsRepo: MealTimeRepository,
    private val waterRepo: WaterRepository
) : ViewModel() {

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

    // Суммарные БЖУ за сегодня
    val proteinsToday: StateFlow<Int> = mealsRepo.getProteinsToday()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)
    val fatsToday: StateFlow<Int> = mealsRepo.getFatsToday()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)
    val carbsToday: StateFlow<Int> = mealsRepo.getCarbsToday()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)
}

