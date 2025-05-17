package com.example.myhealth.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_intake")
data class WaterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amountMl: Float,
    val timestamp: Long = System.currentTimeMillis()
)