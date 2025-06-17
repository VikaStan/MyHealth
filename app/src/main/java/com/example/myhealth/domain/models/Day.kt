package com.example.myhealth.domain.models

import java.time.LocalDate


class Day(
    val date: LocalDate,
    var totalCalories: Int = 0,
    var goalCalories: Int,
    var isStrike: Boolean = false,
    var mealTimeList: MutableList<MealTime>,
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
        isStrike = foodCount >= 3 && totalCalories <= goalCalories
    }

    fun dayToMillis(): Long = java.util.Calendar.getInstance().apply {
        set(date.year, date.month.value - 1, date.dayOfMonth)
    }.timeInMillis

    fun dayOfWeekToString(): Int {
        TODO("Not yet implemented")
    }
}