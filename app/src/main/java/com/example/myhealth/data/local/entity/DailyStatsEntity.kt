package com.example.myhealth.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_stats")
data class DailyStatsEntity(
    @PrimaryKey val date: String,          // yyyy-MM-dd
    val steps: Int,
    val calories: Int
)