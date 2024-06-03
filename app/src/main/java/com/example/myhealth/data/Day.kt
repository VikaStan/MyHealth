package com.example.myhealth.data

import com.example.myhealth.R
import java.time.LocalDate
import java.util.Calendar

class Day(
    val date: LocalDate,
    var foodCount: Int = 0,
    var totalCalories: Int = 0,
    var goalCalories: Int = 1000,
    var totalSleep: Float = 0f,
    var goalSleep: Float = 8f,

    var breakfast: Food = Food(
        products = mutableListOf(
            Product(Product.Soup, 100, 100f,10, "Покушал хорошо, все гуд")
        ),
        FoodTimeType.Breakfast
    ),
    var lunch: Food = Food(
        products = mutableListOf(
            Product(Product.Eggs, 100, 100f, 10,"")
        ),
        FoodTimeType.Lunch
    ),
    var dinner: Food = Food(
        products = mutableListOf(
            Product(Product.Eggs, 100, 100f, 10,"")
        ),
        FoodTimeType.Dinner
    ),
    var bedTime: MutableList<Sleep> = mutableListOf(
        Sleep(hours = 1f, " ", true)
    )
) {

    init {
        updateAllCount()
    }

    fun updateAllCount(){
        foodCount=0
        totalSleep=0f
        totalCalories=0
        for (i in listOf(breakfast, lunch, dinner)) {
            if (i.products.isNotEmpty()) {
                i.products.forEach {
                    foodCount++
                    totalCalories += it.caloriesPer100Gramms
                }

            }
        }
        bedTime.forEach { sleep ->
            totalSleep += sleep.hours
        }
    }
    fun dayOfWeekToString() = when (this.date.dayOfWeek.value) {
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

    fun calcBadTime(): Float {
        bedTime.forEach { sleep ->
            totalSleep += sleep.hours
        }
        return totalSleep
    }

}

class Food(
    var products: MutableList<Product>,
    val foodTimeType: FoodTimeType
)

enum class FoodTimeType(val n: String){
    Breakfast("Breakfast"),
    Lunch("Lunch"),
    Dinner("Dinner")
}
class Sleep(
    var hours: Float=0f, var description: String = "", var isAlarmed: Boolean = false
)

