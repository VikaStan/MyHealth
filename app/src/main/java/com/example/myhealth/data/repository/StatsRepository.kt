package com.example.myhealth.data.repository

import com.example.myhealth.domain.repository.WaterRepository
import com.example.myhealth.presentation.statistics.StatisticsState
import com.example.myhealth.presentation.statistics.StatsTab
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatsRepository @Inject constructor(
    private val stepsRepository: StepsRepository,
    private val waterRepository: WaterRepository,
) {
    private val currentTab = MutableStateFlow(StatsTab.ACTIVITY)
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _statsState: StateFlow<StatisticsState> = combine(
        currentTab,
        stepsRepository.getStepsForLastWeek(),
        waterRepository.getWaterForLastWeek()
    ) { tab, steps, water ->
        StatisticsState(
            currentTab = tab,
            weeklySteps = steps,
            avgSteps = if (steps.isNotEmpty()) steps.average().toInt() else 0,
            weeklyWater = water,
            avgWater = if (water.isNotEmpty()) water.average().toInt() else 0
        )
    }.stateIn(scope, SharingStarted.Eagerly, StatisticsState())

    val statsState: StateFlow<StatisticsState> = _statsState

    fun setCurrentTab(tab: StatsTab) {
        currentTab.value = tab
    }
}

