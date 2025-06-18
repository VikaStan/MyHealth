package com.example.myhealth.domain.models

import com.example.myhealth.R
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
        return when (date.dayOfWeek) {
            java.time.DayOfWeek.MONDAY -> R.string.mon
            java.time.DayOfWeek.TUESDAY -> R.string.tue
            java.time.DayOfWeek.WEDNESDAY -> R.string.wed
            java.time.DayOfWeek.THURSDAY -> R.string.tue
            java.time.DayOfWeek.FRIDAY -> R.string.fri
            java.time.DayOfWeek.SATURDAY -> R.string.sat
            java.time.DayOfWeek.SUNDAY -> R.string.sun
        }
    }
}