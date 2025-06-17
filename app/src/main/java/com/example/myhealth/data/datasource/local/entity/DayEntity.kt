package com.example.myhealth.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myhealth.domain.models.Day
import com.example.myhealth.domain.models.MealTime
import com.example.myhealth.room.converters.DateConverter
import com.example.myhealth.room.converters.MealTimeListConverter
import java.time.LocalDate

@Entity(tableName = "days")
data class DayEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @TypeConverters(DateConverter::class) val date: LocalDate,
    var totalCalories: Int = 0,
    var goalCalories: Int = 0,
    var isStrike: Boolean = false,
    @TypeConverters(MealTimeListConverter::class) var mealTimeList: MutableList<MealTime> = mutableListOf()
) {
    fun toDomain() = Day(
        date = date,
        totalCalories = totalCalories,
        goalCalories = goalCalories,
        isStrike = isStrike,
        mealTimeList = mealTimeList
    )

    companion object {
        fun fromDomain(day: Day) = DayEntity(
            date = day.date,
            totalCalories = day.totalCalories,
            goalCalories = day.goalCalories,
            isStrike = day.isStrike,
            mealTimeList = day.mealTimeList
        )
    }
}