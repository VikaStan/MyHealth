package com.example.myhealth.domain.models

data class Stats(
    val steps: Int = 0,
    /** Выпитая за день вода в мл. */
    val waterDrunk: Float = 0f,
    /** Дневная норма воды в мл. */
    val dailyTargetWater: Float = DEFAULT_DAILY_WATER
) {
    companion object {
        const val DEFAULT_DAILY_WATER = 2000f
    }
}