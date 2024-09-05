package com.example.myhealth.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import com.example.myhealth.room.Converters.DateConverter
import com.example.myhealth.R
import java.time.LocalDate
import java.util.Calendar

@Entity(tableName = "days")
class DayOld(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @TypeConverters(DateConverter::class)
    val date: LocalDate,
    var foodCount: Int = 0,
    var totalCalories: Int = 0,
    var goalCalories: Int = 1000,
    var totalSleep: Float = 0f,
    var goalSleep: Float = 8f,
    var isStrike: Boolean = false,
) {

    @Relation(parentColumn = "id", entityColumn = "food_id")
    var breakfast: Food = Food(
        productOlds = mutableListOf(
        ),
        foodTimeType = FoodTimeType.Breakfast
    )

    @Ignore
    var lunch: Food = Food(
        productOlds = mutableListOf(
        ),
        foodTimeType = FoodTimeType.Lunch
    )

    @Ignore
    var dinner: Food = Food(
        productOlds = mutableListOf(
        ),
        foodTimeType = FoodTimeType.Dinner
    )

    @Ignore
    var bedTime: MutableList<Sleep> = mutableListOf(
    )


    init {
        updateAllCount()
    }

    fun updateAllCount() {
        foodCount = 0
        totalSleep = 0f
        totalCalories = 0
        for (i in listOf(breakfast, lunch, dinner)) {
            if (i.productOlds.isNotEmpty()) {
                i.productOlds.forEach {
                    foodCount++
                    totalCalories += it.caloriesPer100Gramms
                }

            }
        }
        bedTime.forEach { sleep ->
            totalSleep += sleep.hours
        }
        isStrike =
            if (bedTime.isNotEmpty() && foodCount >= 3) totalSleep <= goalSleep && totalCalories <= goalCalories else false
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
            this@DayOld.date.year,
            this@DayOld.date.month.value - 1,
            this@DayOld.date.dayOfMonth,
        )
    }.timeInMillis

    fun calcBadTime(): Float {
        bedTime.forEach { sleep ->
            totalSleep += sleep.hours
        }
        return totalSleep
    }

}

@Entity(tableName = "foods")
class Food(
    val food_id: Int = 0,
    var productOlds: MutableList<ProductOld>,
    val foodTimeType: FoodTimeType
) {
    fun getFoodTimeCalories(): Float {
        var summ = 0f
        productOlds.forEach { summ += it.caloriesPer100Gramms / 100 * it.gramms }
        return summ
    }
}

enum class FoodTimeType(val n: String) {
    Breakfast("Breakfast"),
    Lunch("Lunch"),
    Dinner("Dinner")
}

class Sleep(
    var hours: Float = 0f, var description: String = "", var isAlarmed: Boolean = false
)

