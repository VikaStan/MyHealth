package com.example.myhealth.presentation.dashboard

data class DashboardState(
    val steps: Int = 0,
    val waterDrunk: Int = 0,
    val caloriesBurned: Int = 0,
    val weeklySteps: List<Int> = emptyList()
)