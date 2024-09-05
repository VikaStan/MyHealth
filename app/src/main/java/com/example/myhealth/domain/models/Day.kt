package com.example.myhealth.domain.models

import java.time.Duration
import java.time.LocalDate


class Day(
    val date: LocalDate,
    var totalCalories: Int = 0,
    var goalCalories: Int,
    var totalSleep: Duration = Duration.ofMinutes(0),
    var goalSleep: Float,
    var isStrike: Boolean = false,
    var mealTimeList: MutableList<MealTime>,
    var sleepTimeList: MutableList<SleepTime>
) {
}