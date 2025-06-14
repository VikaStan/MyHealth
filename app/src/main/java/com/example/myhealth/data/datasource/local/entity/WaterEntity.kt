package com.example.myhealth.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myhealth.domain.models.WaterLog

@Entity(tableName = "water_intake")
data class WaterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amountMl: Float,
    val timestamp: Long = System.currentTimeMillis()
)
fun WaterEntity.toDomain() = WaterLog(id, volume, time)