package com.example.myhealth.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myhealth.domain.models.Day
import com.example.myhealth.domain.models.MealTime
import com.example.myhealth.domain.models.SleepTime
import com.example.myhealth.room.converters.DateConverter
import com.example.myhealth.room.converters.MealTimeListConverter
import com.example.myhealth.room.converters.SleepTimeListConverter
import java.time.Duration
import java.time.LocalDate

@Entity(tableName = "days")
data class DayEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @TypeConverters(DateConverter::class) val date: LocalDate,
    var totalCalories: Int = 0,
    var goalCalories: Int = 0,
    var totalSleepMinutes: Long = 0,
    var goalSleep: Float = 0f,
    var isStrike: Boolean = false,
    @TypeConverters(MealTimeListConverter::class) var mealTimeList: MutableList<MealTime> = mutableListOf(),
    @TypeConverters(SleepTimeListConverter::class) var sleepTimeList: MutableList<SleepTime> = mutableListOf()
) {
    fun toDomain() = Day(
        date = date,
        totalCalories = totalCalories,
        goalCalories = goalCalories,
        totalSleep = Duration.ofMinutes(totalSleepMinutes),
        goalSleep = goalSleep,
        isStrike = isStrike,
        mealTimeList = mealTimeList,
        sleepTimeList = sleepTimeList
    )

    companion object {
        fun fromDomain(day: Day) = DayEntity(
            date = day.date,
            totalCalories = day.totalCalories,
            goalCalories = day.goalCalories,
            totalSleepMinutes = day.totalSleep.toMinutes(),
            goalSleep = day.goalSleep,
            isStrike = day.isStrike,
            mealTimeList = day.mealTimeList,
            sleepTimeList = day.sleepTimeList
        )
    }
}