package com.example.myhealth.presentation.statistics

enum class StatsTab { ACTIVITY, HYDRATION }

data class StatisticsState(
    val currentTab: StatsTab = StatsTab.ACTIVITY,
    val weeklySteps: List<Int> = emptyList(),
    val avgSteps: Int = 0,
    val weeklyWater: List<Int> = emptyList(),
    val avgWater: Int = 0
)