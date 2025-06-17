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
    fun updateAllCount() {
        totalCalories = 0
        var foodCount = 0
        mealTimeList.forEach { meal ->
            meal.productCount = meal.productList.size
            meal.totalCalories = meal.productList.sumOf { it.caloriesPer100Gramm * it.gramms / 100 }
            totalCalories += meal.totalCalories
            foodCount += meal.productCount
        }
        totalSleep = Duration.ofMinutes(sleepTimeList.sumOf { it.duration.toMinutes() })
        isStrike = if (sleepTimeList.isNotEmpty() && foodCount >= 3) {
            totalSleep.toHours() <= goalSleep && totalCalories <= goalCalories
        } else false
    }

    fun dayToMillis(): Long = java.util.Calendar.getInstance().apply {
        set(date.year, date.month.value - 1, date.dayOfMonth)
    }.timeInMillis

    fun dayOfWeekToString(): Int {
        TODO("Not yet implemented")
    }
}