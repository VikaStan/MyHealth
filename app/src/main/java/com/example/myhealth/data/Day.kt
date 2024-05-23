package com.example.myhealth.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FoodBank
import com.example.myhealth.R
import java.time.LocalDate
import java.util.Calendar

class Day(
    val date: LocalDate,
    var foodCount: Int = 0,
    var totalCalories: Int = 10,
    var goalCalories: Int = 1000,
    var totalSleep: Float = 0f,
    var goalSleep: Float = 8f,
    var breakfast: Food = Food(
        products = mutableMapOf(
            Product(
                Icons.Default.FoodBank,
                "Макарошки",
                3500
            ) to 2
        ), 100
    ),
    var lunch: Food = Food(
        products = mutableMapOf(
            Product(
                Icons.Default.FoodBank,
                "Макарошки",
                1000
            ) to 2
        ), 100
    ),
    var dinner: Food = Food(
        products = mutableMapOf(
            Product(
                Icons.Default.FoodBank,
                "Макарошки",
                1000
            ) to 2
        ), 100
    ),
    var bedTime: MutableMap<Sleep, Int> = mutableMapOf(
        Sleep(hours = 3f, " ", true) to 2
    )
) {

    init {
        for (i in listOf(breakfast, lunch, dinner)) {
            if (i.products.isNotEmpty()) {
                foodCount++
                totalCalories += i.calories
            }
        }
        bedTime.forEach { sleep, count ->
            totalSleep+=sleep.hours*count
        }
    }

    fun dayOfWeekToString() =
        when (this.date.dayOfWeek.value) {
            1 -> R.string.mon
            2 -> R.string.thu
            3 -> R.string.wed
            4 -> R.string.thu
            5 -> R.string.fri
            6 -> R.string.sat
            7 -> R.string.sun
            else -> {
                R.string.data
            }
        }


    fun dayToMillis() = Calendar.getInstance().apply {
        set(
            this@Day.date.year,
            this@Day.date.month.value - 1,
            this@Day.date.dayOfMonth,
        )
    }.timeInMillis

    fun calcBadTime():Float {
        bedTime.forEach { sleep, count ->
            totalSleep+=sleep.hours*count
        }
        return totalSleep
    }
}

class Food(
    var products: MutableMap<Product, Int>,
    var calories: Int
) {
    fun addFood(product: Product, count: Int) {
        products[product] = count
        calories += product.calories * count
    }
}

class Sleep(
    var hours: Float,
    var description: String,
    var isAlarmed: Boolean
)

